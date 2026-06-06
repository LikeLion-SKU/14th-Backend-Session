package com.example.likelionbe.domain.contract.repository;

import com.example.likelionbe.domain.contract.entity.Contract;
import com.example.likelionbe.domain.contract.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    /**
     * 특정 매물의 계약 목록을 생성일 내림차순으로 조회합니다.
     *
     * @param listingId 매물 ID
     * @return 계약 목록
     */
    List<Contract> findAllByListingIdOrderByCreatedAtDesc(Long listingId);

    /**
     * 특정 매물에 특정 구매자의 계약 신청이 존재하는지 확인합니다. (중복 신청 방지)
     *
     * @param listingId 매물 ID
     * @param buyerId   구매자 ID
     * @return 존재 여부
     */
    boolean existsByListingIdAndBuyerId(Long listingId, Long buyerId);

    /**
     * 특정 매물에 특정 상태의 계약이 존재하는지 확인합니다.
     *
     * @param listingId      매물 ID
     * @param contractStatus 계약 상태
     * @return 존재 여부
     */
    boolean existsByListingIdAndContractStatus(Long listingId, ContractStatus contractStatus);

    /**
     * 구매자의 계약 목록을 생성일 내림차순으로 조회합니다.
     *
     * @param buyerId 구매자 ID
     * @return 계약 목록
     */
    List<Contract> findAllByBuyerIdOrderByCreatedAtDesc(Long buyerId);
}
