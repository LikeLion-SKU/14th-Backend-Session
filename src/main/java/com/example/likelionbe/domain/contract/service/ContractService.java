package com.example.likelionbe.domain.contract.service;

import com.example.likelionbe.domain.contract.dto.*;
import com.example.likelionbe.domain.contract.entity.Checklist;
import com.example.likelionbe.domain.contract.entity.Contract;
import com.example.likelionbe.domain.contract.enums.ChecklistCode;
import com.example.likelionbe.domain.contract.enums.ContractStatus;
import com.example.likelionbe.domain.contract.exception.ContractErrorCode;
import com.example.likelionbe.domain.contract.mapper.ChecklistMapper;
import com.example.likelionbe.domain.contract.mapper.ContractMapper;
import com.example.likelionbe.domain.contract.repository.ChecklistRepository;
import com.example.likelionbe.domain.contract.repository.ContractRepository;
import com.example.likelionbe.domain.listing.entity.Listing;
import com.example.likelionbe.domain.listing.enums.ListingStatus;
import com.example.likelionbe.domain.listing.exception.ListingErrorCode;
import com.example.likelionbe.domain.listing.repository.ListingRepository;
import com.example.likelionbe.domain.user.entity.User;
import com.example.likelionbe.domain.user.entity.UserType;
import com.example.likelionbe.domain.user.repository.UserRepository;
import com.example.likelionbe.global.exception.CustomException;
import com.example.likelionbe.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ChecklistRepository checklistRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ContractMapper contractMapper;
    private final ChecklistMapper checklistMapper;

    /**
     * 계약 신청을 생성합니다.
     * 구매자가 특정 매물에 계약을 신청하고, 기본 체크리스트 7건을 자동으로 생성합니다.
     *
     * @param listingId 매물 ID
     * @param reqDto    계약 신청 요청 DTO
     * @param buyerId   구매자 사용자 ID
     * @return 계약 신청 응답 DTO
     */
    @Transactional
    public ContractApplyResDto createContract(Long listingId, CreateContractReqDto reqDto, Long buyerId) {
        log.info("[ContractService] createContract() - START : 계약 신청 | listingId: {}, buyerId: {}", listingId, buyerId);

        /** (1) 구매자 조회 및 권한 검증 */
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        validateBuyer(buyer);

        /** (2) 매물 조회 및 유효성 검증 */
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new CustomException(ListingErrorCode.LISTING_NOT_FOUND));
        if (listing.getListingStatus() == ListingStatus.DELETED) {
            throw new CustomException(ListingErrorCode.LISTING_NOT_FOUND);
        }

        /** (3) 본인 매물 신청 불가 검증 */
        if (listing.getSeller().getId().equals(buyerId)) {
            throw new CustomException(ContractErrorCode.CONTRACT_SELF_LISTING);
        }

        /** (4) AVAILABLE 상태인지 검증 */
        if (listing.getListingStatus() != ListingStatus.AVAILABLE) {
            throw new CustomException(ContractErrorCode.CONTRACT_LISTING_NOT_AVAILABLE);
        }

        /** (5) 중복 신청 검증 */
        if (contractRepository.existsByListingIdAndBuyerId(listingId, buyerId)) {
            throw new CustomException(ContractErrorCode.CONTRACT_ALREADY_EXISTS);
        }

        /** (6) Contract 엔티티 생성 */
        Contract contract = Contract.builder()
                .listing(listing)
                .buyer(buyer)
                .requestMessage(reqDto.requestMessage())
                .contractStatus(ContractStatus.REQUESTED)
                .contractSalePrice(listing.getSalePrice())
                .contractDeposit(listing.getDeposit())
                .contractMonthlyRent(listing.getMonthlyRent())
                .build();

        contract = contractRepository.save(contract);

        /** (7) 체크리스트 7건 자동 생성 */
        int checklistsCreated = createChecklists(contract);

        ContractApplyResDto result = contractMapper.toContractApplyResDto(contract, checklistsCreated);

        log.info("[ContractService] createContract() - END : 계약 신청 완료 | contractId: {}", contract.getId());
        return result;
    }

    /**
     * 특정 매물의 계약 목록을 판매자 관점에서 조회합니다.
     *
     * @param listingId 매물 ID
     * @param sellerId  판매자 사용자 ID
     * @return 판매자용 계약 요약 DTO 목록
     */
    @Transactional(readOnly = true)
    public List<SellerContractSummaryResDto> getSellerContracts(Long listingId, Long sellerId) {
        log.info("[ContractService] getSellerContracts() - START : 판매자 계약 목록 조회 | listingId: {}, sellerId: {}", listingId, sellerId);

        /** (1) 매물 조회 및 본인 매물 검증 */
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new CustomException(ListingErrorCode.LISTING_NOT_FOUND));
        validateListingOwner(listing, sellerId);

        /** (2) 계약 목록 조회 */
        List<Contract> contracts = contractRepository.findAllByListingIdOrderByCreatedAtDesc(listingId);

        List<SellerContractSummaryResDto> result = contracts.stream()
                .map(contractMapper::toSellerContractSummaryResDto)
                .toList();

        log.info("[ContractService] getSellerContracts() - END : 판매자 계약 목록 조회 완료 | count: {}", result.size());
        return result;
    }

    /**
     * 계약 상태를 변경합니다. (판매자 전용)
     * REQUESTED → APPROVED/REJECTED, APPROVED → COMPLETED 상태 전이를 지원합니다.
     *
     * @param contractId 계약 ID
     * @param reqDto     상태 변경 요청 DTO
     * @param sellerId   판매자 사용자 ID
     * @return 변경된 계약 상태 응답 DTO
     */
    @Transactional
    public ContractStatusResDto updateContractStatus(Long contractId, UpdateContractStatusReqDto reqDto, Long sellerId) {
        log.info("[ContractService] updateContractStatus() - START : 계약 상태 변경 | contractId: {}, newStatus: {}, sellerId: {}",
                contractId, reqDto.contractStatus(), sellerId);

        /** (1) 계약 조회 */
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        /** (2) 매물 소유자 검증 */
        validateListingOwner(contract.getListing(), sellerId);

        /** (3) 판매자 권한 검증 */
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        validateSeller(seller);

        ContractStatus currentStatus = contract.getContractStatus();
        ContractStatus newStatus = reqDto.contractStatus();

        /** (4) 상태 전이 검증 및 처리 */
        if (currentStatus == ContractStatus.REQUESTED && newStatus == ContractStatus.APPROVED) {
            // 다른 APPROVED 계약 존재 확인
            if (contractRepository.existsByListingIdAndContractStatus(
                    contract.getListing().getId(), ContractStatus.APPROVED)) {
                throw new CustomException(ContractErrorCode.CONTRACT_ALREADY_APPROVED);
            }
            contract.updateStatus(ContractStatus.APPROVED);
            contract.getListing().updateStatus(ListingStatus.CONTRACT_PENDING);

        } else if (currentStatus == ContractStatus.REQUESTED && newStatus == ContractStatus.REJECTED) {
            contract.updateStatus(ContractStatus.REJECTED);

        } else if (currentStatus == ContractStatus.APPROVED && newStatus == ContractStatus.COMPLETED) {
            contract.updateStatus(ContractStatus.COMPLETED);
            contract.getListing().updateStatus(ListingStatus.CONTRACTED);

        } else {
            throw new CustomException(ContractErrorCode.CONTRACT_INVALID_STATUS_TRANSITION);
        }

        ContractStatusResDto result = contractMapper.toContractStatusResDto(contract);

        log.info("[ContractService] updateContractStatus() - END : 계약 상태 변경 완료 | contractId: {}, status: {}",
                contractId, newStatus);
        return result;
    }

    /**
     * 계약 상태를 조회합니다. (구매자 전용)
     *
     * @param contractId 계약 ID
     * @param buyerId    구매자 사용자 ID
     * @return 계약 상태 응답 DTO
     */
    @Transactional(readOnly = true)
    public ContractStatusResDto getContractStatus(Long contractId, Long buyerId) {
        log.info("[ContractService] getContractStatus() - START : 계약 상태 조회 | contractId: {}, buyerId: {}", contractId, buyerId);

        /** (1) 계약 조회 */
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        /** (2) 본인 계약 검증 */
        validateContractBuyer(contract, buyerId);

        ContractStatusResDto result = contractMapper.toContractStatusResDto(contract);

        log.info("[ContractService] getContractStatus() - END : 계약 상태 조회 완료 | contractId: {}, status: {}",
                contractId, contract.getContractStatus());
        return result;
    }

    /**
     * 계약의 체크리스트를 조회합니다. (구매자 전용)
     *
     * @param contractId 계약 ID
     * @param buyerId    구매자 사용자 ID
     * @return 체크리스트 응답 DTO
     */
    @Transactional(readOnly = true)
    public ChecklistResDto getChecklist(Long contractId, Long buyerId) {
        log.info("[ContractService] getChecklist() - START : 체크리스트 조회 | contractId: {}, buyerId: {}", contractId, buyerId);

        /** (1) 계약 조회 */
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        /** (2) 본인 계약 검증 */
        validateContractBuyer(contract, buyerId);

        /** (3) 체크리스트 조회 (sortOrder asc) */
        List<Checklist> checklists = checklistRepository.findAllByContractIdOrderBySortOrderAsc(contractId);

        ChecklistResDto result = checklistMapper.toChecklistResDto(contractId, checklists);

        log.info("[ContractService] getChecklist() - END : 체크리스트 조회 완료 | contractId: {}, itemCount: {}",
                contractId, checklists.size());
        return result;
    }

    /**
     * 체크리스트 항목의 체크 상태를 업데이트합니다. (구매자 전용)
     *
     * @param contractId 계약 ID
     * @param reqDto     체크리스트 수정 요청 DTO
     * @param buyerId    구매자 사용자 ID
     * @return 업데이트된 체크리스트 응답 DTO
     */
    @Transactional
    public ChecklistResDto updateChecklist(Long contractId, UpdateChecklistReqDto reqDto, Long buyerId) {
        log.info("[ContractService] updateChecklist() - START : 체크리스트 수정 | contractId: {}, buyerId: {}", contractId, buyerId);

        /** (1) 계약 조회 */
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        /** (2) 본인 계약 검증 */
        validateContractBuyer(contract, buyerId);

        /** (3) 수정 가능 상태 검증 (REJECTED/CANCELLED 상태는 수정 불가) */
        if (contract.getContractStatus() == ContractStatus.REJECTED
                || contract.getContractStatus() == ContractStatus.CANCELLED) {
            throw new CustomException(ContractErrorCode.CONTRACT_NOT_MODIFIABLE);
        }

        /** (4) 요청된 체크리스트 항목 업데이트 */
        for (var item : reqDto.items()) {
            Checklist checklist = checklistRepository.findById(item.checklistId())
                    .orElseThrow(() -> new CustomException(ContractErrorCode.CHECKLIST_NOT_FOUND));
            checklist.updateChecked(item.checked());
        }

        /** (5) 전체 목록 재조회 후 반환 */
        List<Checklist> checklists = checklistRepository.findAllByContractIdOrderBySortOrderAsc(contractId);
        ChecklistResDto result = checklistMapper.toChecklistResDto(contractId, checklists);

        log.info("[ContractService] updateChecklist() - END : 체크리스트 수정 완료 | contractId: {}", contractId);
        return result;
    }

    /**
     * 계약서 문서(AI 해석, 유의사항 포함)를 조회합니다. (구매자 전용)
     * APPROVED 또는 COMPLETED 상태에서만 조회 가능합니다.
     *
     * @param contractId 계약 ID
     * @param buyerId    구매자 사용자 ID
     * @return 계약서 문서 응답 DTO
     */
    @Transactional(readOnly = true)
    public ContractDocumentResDto getContractDocument(Long contractId, Long buyerId) {
        log.info("[ContractService] getContractDocument() - START : 계약서 문서 조회 | contractId: {}, buyerId: {}", contractId, buyerId);

        /** (1) 계약 조회 */
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        /** (2) 본인 계약 검증 */
        validateContractBuyer(contract, buyerId);

        /** (3) 상태 검증 (APPROVED 또는 COMPLETED만 허용) */
        if (contract.getContractStatus() != ContractStatus.APPROVED
                && contract.getContractStatus() != ContractStatus.COMPLETED) {
            throw new CustomException(ContractErrorCode.CONTRACT_DOCUMENT_NOT_READY);
        }

        ContractDocumentResDto result = contractMapper.toContractDocumentResDto(contract);

        log.info("[ContractService] getContractDocument() - END : 계약서 문서 조회 완료 | contractId: {}", contractId);
        return result;
    }

    /**
     * 계약 생성 시 체크리스트 7건을 자동으로 생성합니다.
     *
     * @param contract 체크리스트를 생성할 계약 엔티티
     * @return 생성된 체크리스트 개수
     */
    private int createChecklists(Contract contract) {
        Map<ChecklistCode, String[]> checklistData = Map.ofEntries(
                Map.entry(ChecklistCode.VERIFY_PARTY_IDENTITY,
                        new String[]{"계약 당사자 신원 확인", "판매자/구매자 이름, 연락처, 신분 정보가 정확한지 확인하세요"}),
                Map.entry(ChecklistCode.CHECK_PROPERTY_REGISTRY,
                        new String[]{"등기부등본 확인", "소유자, 근저당권, 가압류 등 권리관계를 확인하세요"}),
                Map.entry(ChecklistCode.REVIEW_PAYMENT_PLAN,
                        new String[]{"금액 및 지급 일정 확인", "매매가/보증금/월세 및 계약금·잔금 일정을 확인하세요"}),
                Map.entry(ChecklistCode.REVIEW_SPECIAL_TERMS,
                        new String[]{"특약 사항 검토", "계약 특약 문구와 책임 범위를 확인하세요"}),
                Map.entry(ChecklistCode.CONFIRM_BROKER_FEE,
                        new String[]{"중개수수료 확인", "중개보수 금액과 지급 시점을 확인하세요"}),
                Map.entry(ChecklistCode.CONFIRM_MOVE_IN_SCHEDULE,
                        new String[]{"입주/인도 일정 확인", "입주 가능일, 잔금일, 열쇠 인도일을 확인하세요"}),
                Map.entry(ChecklistCode.ARCHIVE_CONTRACT_DOCUMENT,
                        new String[]{"계약서 보관", "계약서 원본/사본 및 안내 문서를 안전하게 보관하세요"})
        );

        int order = 1;
        for (var entry : checklistData.entrySet()) {
            ChecklistCode code = entry.getKey();
            String[] data = entry.getValue();

            Checklist checklist = Checklist.builder()
                    .contract(contract)
                    .checklistCode(code)
                    .title(data[0])
                    .description(data[1])
                    .sortOrder(order++)
                    .required(true)
                    .checked(false)
                    .build();

            checklistRepository.save(checklist);
        }

        return checklistData.size();
    }

    /**
     * 구매자 권한을 검증합니다.
     *
     * @param user 검증할 사용자
     * @throws CustomException 구매자가 아닌 경우
     */
    private void validateBuyer(User user) {
        if (user.getUserType() != UserType.BUYER) {
            throw new CustomException(ContractErrorCode.CONTRACT_BUYER_ONLY);
        }
    }

    /**
     * 판매자 권한을 검증합니다.
     *
     * @param user 검증할 사용자
     * @throws CustomException 판매자가 아닌 경우
     */
    private void validateSeller(User user) {
        if (user.getUserType() != UserType.SELLER) {
            throw new CustomException(ContractErrorCode.CONTRACT_SELLER_ONLY);
        }
    }

    /**
     * 계약의 구매자와 요청 사용자가 일치하는지 검증합니다.
     *
     * @param contract 검증할 계약
     * @param buyerId  요청 사용자 ID
     * @throws CustomException 구매자가 아닌 경우
     */
    private void validateContractBuyer(Contract contract, Long buyerId) {
        if (!contract.getBuyer().getId().equals(buyerId)) {
            throw new CustomException(ContractErrorCode.CONTRACT_NOT_BUYER);
        }
    }

    /**
     * 매물의 소유자와 요청 사용자가 일치하는지 검증합니다.
     *
     * @param listing  검증할 매물
     * @param sellerId 요청 사용자 ID
     * @throws CustomException 소유자가 아닌 경우
     */
    private void validateListingOwner(Listing listing, Long sellerId) {
        if (!listing.getSeller().getId().equals(sellerId)) {
            throw new CustomException(ListingErrorCode.LISTING_NOT_OWNER);
        }
    }
}
