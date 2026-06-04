package com.likelion.besession.domain.checklist.repository;

import com.likelion.besession.domain.checklist.entity.CheckList;
import com.likelion.besession.domain.checklist.entity.CheckListStatus;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    // 계약 상태별 체크리스트 조회
    List<CheckList> findAllByContractAndPhase(Contract contract, ContractStatus phase);

    // 현재 계약 단계에서 완료하지 않은 체크리스트가 있는지 확인
    boolean existsByContractAndPhaseAndCheckListStatus(Contract contract, ContractStatus phase, CheckListStatus checkListStatus);
}
