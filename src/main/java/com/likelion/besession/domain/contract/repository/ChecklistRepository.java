package com.likelion.besession.domain.contract.repository;

import com.likelion.besession.domain.contract.entity.Checklist;
import com.likelion.besession.domain.contract.entity.ContractPhase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findByContractContractId(Long contractId);

    List<Checklist> findByContractContractIdAndPhase(Long contractId, ContractPhase phase);
}
