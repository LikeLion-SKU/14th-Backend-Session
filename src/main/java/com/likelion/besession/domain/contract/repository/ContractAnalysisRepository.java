package com.likelion.besession.domain.contract.repository;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractAnalysisRepository extends JpaRepository<ContractAnalysis, Long> {

    // 계약서에 대한 AI 분석 결과 조회
    Optional<ContractAnalysis> findByContract(Contract contract);

    // 이미 분석된 계약서인지 확인 (중복 분석 방지용)
    boolean existsByContract(Contract contract);
}
