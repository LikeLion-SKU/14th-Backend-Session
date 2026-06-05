package com.example.week09likelion.domain.contract.service;

import com.example.week09likelion.domain.contract.dto.response.ContractReportResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ContractReportService {

    // 계약서 분석 기록 조회
    public ContractReportResponse getContractReport() {
        return ContractReportResponse.defaultReport();
    }
}