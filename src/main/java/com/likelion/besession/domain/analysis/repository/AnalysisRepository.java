package com.likelion.besession.domain.analysis.repository;

import com.likelion.besession.domain.analysis.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    List<Analysis> findByContractIdOrderByCreatedAtDesc(Long contractId);

    List<Analysis> findByContractUserIdOrderByCreatedAtDesc(Long userId);
}
