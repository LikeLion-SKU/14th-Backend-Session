package com.likelion.besession.domain.report.repository;

import com.likelion.besession.domain.report.entity.AiReport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiReportRepository extends JpaRepository<AiReport, Long> {

    Optional<AiReport> findByContractContractId(Long contractId);
}
