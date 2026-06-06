package com.example.likelionbe.domain.listing.service;

import com.example.likelionbe.domain.listing.dto.CreateListingReqDto;
import com.example.likelionbe.domain.listing.dto.ListingDetailResDto;
import com.example.likelionbe.domain.listing.dto.ListingSummaryResDto;
import com.example.likelionbe.domain.listing.dto.UpdateListingReqDto;
import com.example.likelionbe.domain.listing.entity.Listing;
import com.example.likelionbe.domain.listing.enums.ListingStatus;
import com.example.likelionbe.domain.listing.exception.ListingErrorCode;
import com.example.likelionbe.domain.listing.mapper.ListingMapper;
import com.example.likelionbe.domain.listing.repository.ListingRepository;
import com.example.likelionbe.domain.user.entity.User;
import com.example.likelionbe.domain.user.entity.UserType;
import com.example.likelionbe.domain.user.repository.UserRepository;
import com.example.likelionbe.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private final UserRepository userRepository;

    /**
     * 매물을 생성합니다.
     *
     * @param reqDto 매물 생성 요청 DTO
     * @param userId 판매자 사용자 ID
     * @return 생성된 매물 상세 DTO
     */
    @Transactional
    public ListingDetailResDto createListing(CreateListingReqDto reqDto, Long userId) {
        log.info("[ListingService] createListing() - START : 매물 생성 | userId: {}", userId);

        /** (1) 사용자 조회 및 판매자 권한 검증 */
        User seller = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ListingErrorCode.LISTING_NOT_OWNER));
        validateSeller(seller);

        /** (2) Listing 엔티티 생성 및 저장 */
        Listing listing = Listing.builder()
                .seller(seller)
                .title(reqDto.title())
                .description(reqDto.description())
                .propertyType(reqDto.propertyType())
                .transactionType(reqDto.transactionType())
                .listingStatus(ListingStatus.AVAILABLE)
                .salePrice(reqDto.salePrice())
                .deposit(reqDto.deposit())
                .monthlyRent(reqDto.monthlyRent())
                .address(reqDto.address())
                .detailAddress(reqDto.detailAddress())
                .exclusiveArea(reqDto.exclusiveArea())
                .supplyArea(reqDto.supplyArea())
                .roomCount(reqDto.roomCount())
                .bathroomCount(reqDto.bathroomCount())
                .floor(reqDto.floor())
                .totalFloor(reqDto.totalFloor())
                .thumbnailUrl(reqDto.thumbnailUrl())
                .build();

        listing = listingRepository.save(listing);
        log.info("[ListingService] createListing() - END : 매물 생성 완료 | listingId: {}", listing.getId());

        return listingMapper.toListingDetailResDto(listing);
    }

    /**
     * 매물 정보를 수정합니다.
     *
     * @param listingId 수정할 매물 ID
     * @param reqDto    매물 수정 요청 DTO
     * @param userId    요청 사용자 ID
     * @return 수정된 매물 상세 DTO
     */
    @Transactional
    public ListingDetailResDto updateListing(Long listingId, UpdateListingReqDto reqDto, Long userId) {
        log.info("[ListingService] updateListing() - START : 매물 수정 | listingId: {}, userId: {}", listingId, userId);

        /** (1) 매물 조회 */
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new CustomException(ListingErrorCode.LISTING_NOT_FOUND));

        /** (2) 본인 매물 검증 */
        validateListingOwner(listing, userId);

        /** (3) 수정 가능 상태 검증 (DELETED, CONTRACTED 상태는 수정 불가) */
        if (listing.getListingStatus() == ListingStatus.DELETED) {
            throw new CustomException(ListingErrorCode.LISTING_ALREADY_DELETED);
        }
        if (listing.getListingStatus() == ListingStatus.CONTRACTED) {
            throw new CustomException(ListingErrorCode.LISTING_NOT_MODIFIABLE);
        }

        /** (4) 매물 정보 업데이트 */
        listing.updateListing(reqDto);
        log.info("[ListingService] updateListing() - END : 매물 수정 완료 | listingId: {}", listingId);

        return listingMapper.toListingDetailResDto(listing);
    }

    /**
     * 매물을 소프트 삭제합니다.
     *
     * @param listingId 삭제할 매물 ID
     * @param userId    요청 사용자 ID
     */
    @Transactional
    public void deleteListing(Long listingId, Long userId) {
        log.info("[ListingService] deleteListing() - START : 매물 삭제 | listingId: {}, userId: {}", listingId, userId);

        /** (1) 매물 조회 */
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new CustomException(ListingErrorCode.LISTING_NOT_FOUND));

        /** (2) 본인 매물 검증 */
        validateListingOwner(listing, userId);

        /** (3) 삭제 가능 상태 검증 (CONTRACT_PENDING, CONTRACTED 상태는 삭제 불가) */
        if (listing.getListingStatus() == ListingStatus.CONTRACT_PENDING
                || listing.getListingStatus() == ListingStatus.CONTRACTED) {
            throw new CustomException(ListingErrorCode.LISTING_NOT_DELETABLE);
        }

        /** (4) 소프트 삭제 처리 */
        listing.softDelete();
        log.info("[ListingService] deleteListing() - END : 매물 삭제 완료 | listingId: {}", listingId);
    }

    /**
     * DELETED 상태를 제외한 전체 매물 목록을 조회합니다.
     *
     * @return 매물 요약 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<ListingSummaryResDto> getListingList() {
        log.info("[ListingService] getListingList() - START : 매물 목록 조회");

        /** (1) DELETED 제외하고 createdAt 내림차순 조회 */
        List<Listing> listings = listingRepository.findAllByListingStatusNotOrderByCreatedAtDesc(ListingStatus.DELETED);

        log.info("[ListingService] getListingList() - END : 매물 목록 조회 완료 | count: {}", listings.size());

        /** (2) Summary DTO로 변환하여 반환 */
        return listings.stream()
                .map(listingMapper::toListingSummaryResDto)
                .toList();
    }

    /**
     * 매물 단건을 상세 조회합니다.
     *
     * @param listingId 조회할 매물 ID
     * @return 매물 상세 DTO
     */
    @Transactional(readOnly = true)
    public ListingDetailResDto getListing(Long listingId) {
        log.info("[ListingService] getListing() - START : 매물 상세 조회 | listingId: {}", listingId);

        /** (1) DELETED가 아닌 매물 조회 */
        Listing listing = listingRepository.findByIdAndListingStatusNot(listingId, ListingStatus.DELETED)
                .orElseThrow(() -> new CustomException(ListingErrorCode.LISTING_NOT_FOUND));

        log.info("[ListingService] getListing() - END : 매물 상세 조회 완료 | listingId: {}", listingId);

        /** (2) Detail DTO로 변환하여 반환 */
        return listingMapper.toListingDetailResDto(listing);
    }

    /**
     * 사용자의 판매자 권한을 검증합니다.
     *
     * @param user 검증할 사용자
     * @throws CustomException 판매자가 아닌 경우
     */
    private void validateSeller(User user) {
        if (user.getUserType() != UserType.SELLER) {
            throw new CustomException(ListingErrorCode.LISTING_SELLER_ONLY);
        }
    }

    /**
     * 매물의 소유자와 요청 사용자가 일치하는지 검증합니다.
     *
     * @param listing 검증할 매물
     * @param userId  요청 사용자 ID
     * @throws CustomException 소유자가 아닌 경우
     */
    private void validateListingOwner(Listing listing, Long userId) {
        if (!listing.getSeller().getId().equals(userId)) {
            throw new CustomException(ListingErrorCode.LISTING_NOT_OWNER);
        }
    }
}
