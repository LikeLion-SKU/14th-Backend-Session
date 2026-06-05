package com.likjelion.besession.week09_domain.analysis.repository;

import com.likjelion.besession.week09_domain.analysis.entity.Analysis;
import com.likjelion.besession.week09_domain.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    Optional<Analysis> findByContract(Contract contract);
}
