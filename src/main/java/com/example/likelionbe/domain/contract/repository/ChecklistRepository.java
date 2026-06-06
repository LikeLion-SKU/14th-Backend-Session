package com.example.likelionbe.domain.contract.repository;

import com.example.likelionbe.domain.contract.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    /**
     * 특정 계약의 체크리스트 항목을 정렬 순서 오름차순으로 전체 조회합니다.
     *
     * @param contractId 계약 ID
     * @return 체크리스트 항목 목록
     */
    List<Checklist> findAllByContractIdOrderBySortOrderAsc(Long contractId);
}
