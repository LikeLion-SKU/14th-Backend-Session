package com.likelion.besession.domain.checklist.repository;

import com.likelion.besession.domain.checklist.entity.Checklist;
import com.likelion.besession.domain.contract.entity.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findByContractIdAndStageOrderByChecklistOrderAsc(Long contractId, ContractStatus stage);
}
