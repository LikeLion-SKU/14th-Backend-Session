package com.likelion.besession.domain_ia.checklist.repository;

import com.likelion.besession.domain_ia.checklist.entity.Checklist;
import com.likelion.besession.domain_ia.checklist.entity.ContractChecklistStatus;
import com.likelion.besession.domain_ia.contract.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractChecklistStatusRepository extends JpaRepository<ContractChecklistStatus, Long> {
    List<ContractChecklistStatus> findAllByContractIdAndProcess(Long contractId, Process process);

    ContractChecklistStatus findByChecklist(Checklist checklist);
}
