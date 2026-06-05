package com.likjelion.besession.week09_domain.report.service;

import com.likjelion.besession.global.exception.CustomException;
import com.likjelion.besession.global.exception.GlobalErrorCode;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.week09_domain.contract.entity.Contract;
import com.likjelion.besession.week09_domain.contract.repository.ContractRepository;
import com.likjelion.besession.week09_domain.report.dto.response.ReportDetailResponse;
import com.likjelion.besession.week09_domain.report.dto.response.ReportListItemResponse;
import com.likjelion.besession.week09_domain.report.entity.Report;
import com.likjelion.besession.week09_domain.report.repository.ReportRepository;
import com.likjelion.besession.week09_domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final ContractRepository contractRepository;

    public List<ReportListItemResponse> getMyReports(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return reportRepository.findAllByUser(user).stream()
                .map(report -> {
                    Long contractId = contractRepository.findByProperty(report.getProperty())
                            .map(Contract::getId)
                            .orElse(null);
                    return ReportListItemResponse.builder()
                            .reportId(report.getId())
                            .contractId(contractId)
                            .title(report.getTitle())
                            .content(report.getContent())
                            .createdAt(report.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public ReportDetailResponse getContractReport(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        Report report = reportRepository.findByProperty(contract.getProperty())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        return ReportDetailResponse.builder()
                .reportId(report.getId())
                .contractId(contractId)
                .title(report.getTitle())
                .content(report.getContent())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
