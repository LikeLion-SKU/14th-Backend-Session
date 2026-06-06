package com.likelion.besession.domain.report.service;

import com.likelion.besession.domain.contract.exception.ContractNotFoundException;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.report.dto.response.AiReportResponse;
import com.likelion.besession.domain.report.entity.AiReport;
import com.likelion.besession.domain.report.exception.AiReportNotFoundException;
import com.likelion.besession.domain.report.repository.AiReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final AiReportRepository aiReportRepository;
    private final ContractRepository contractRepository;

    public AiReportResponse getAnalysis(Long contractId) {
        contractRepository.findById(contractId)
            .orElseThrow(ContractNotFoundException::new);

        AiReport report = aiReportRepository.findByContractContractId(contractId)
            .orElseThrow(AiReportNotFoundException::new);

        return AiReportResponse.from(report);
    }
}
