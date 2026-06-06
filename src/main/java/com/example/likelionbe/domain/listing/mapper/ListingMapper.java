package com.example.likelionbe.domain.listing.mapper;

import com.example.likelionbe.domain.listing.dto.ListingDetailResDto;
import com.example.likelionbe.domain.listing.dto.ListingSummaryResDto;
import com.example.likelionbe.domain.listing.entity.Listing;
import org.springframework.stereotype.Component;

/**
 * Listing 엔티티와 DTO 간 변환을 담당하는 매퍼 클래스입니다.
 */
@Component
public class ListingMapper {

    /**
     * Listing 엔티티를 ListingSummaryResDto로 변환합니다.
     *
     * @param listing 변환할 Listing 엔티티
     * @return 변환된 ListingSummaryResDto
     */
    public ListingSummaryResDto toListingSummaryResDto(Listing listing) {
        return ListingSummaryResDto.builder()
                .listingId(listing.getId())
                .title(listing.getTitle())
                .address(listing.getAddress())
                .transactionType(listing.getTransactionType())
                .propertyType(listing.getPropertyType())
                .listingStatus(listing.getListingStatus())
                .deposit(listing.getDeposit())
                .monthlyRent(listing.getMonthlyRent())
                .salePrice(listing.getSalePrice())
                .createdAt(listing.getCreatedAt())
                .build();
    }

    /**
     * Listing 엔티티를 ListingDetailResDto로 변환합니다.
     *
     * @param listing 변환할 Listing 엔티티
     * @return 변환된 ListingDetailResDto
     */
    public ListingDetailResDto toListingDetailResDto(Listing listing) {
        return ListingDetailResDto.builder()
                .listingId(listing.getId())
                .sellerId(listing.getSeller().getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .propertyType(listing.getPropertyType())
                .transactionType(listing.getTransactionType())
                .listingStatus(listing.getListingStatus())
                .salePrice(listing.getSalePrice())
                .deposit(listing.getDeposit())
                .monthlyRent(listing.getMonthlyRent())
                .address(listing.getAddress())
                .detailAddress(listing.getDetailAddress())
                .exclusiveArea(listing.getExclusiveArea())
                .supplyArea(listing.getSupplyArea())
                .roomCount(listing.getRoomCount())
                .bathroomCount(listing.getBathroomCount())
                .floor(listing.getFloor())
                .totalFloor(listing.getTotalFloor())
                .thumbnailUrl(listing.getThumbnailUrl())
                .createdAt(listing.getCreatedAt())
                .modifiedAt(listing.getModifiedAt())
                .build();
    }
}
