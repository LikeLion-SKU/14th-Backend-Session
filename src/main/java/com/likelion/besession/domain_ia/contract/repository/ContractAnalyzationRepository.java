package com.likelion.besession.domain_ia.contract.repository;

import com.likelion.besession.domain_ia.contract.entity.Contract;
import com.likelion.besession.domain_ia.contract.entity.ContractAnalyzation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractAnalyzationRepository extends JpaRepository<ContractAnalyzation, Long> {
    ContractAnalyzation findByContract(Contract contract);
}
