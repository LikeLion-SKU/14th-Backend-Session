package com.likjelion.besession.week09_domain.analysis.service;

import com.likjelion.besession.global.exception.CustomException;
import com.likjelion.besession.global.exception.GlobalErrorCode;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.global.service.S3Service;
import com.likjelion.besession.week09_domain.analysis.dto.response.AnalysisResultResponse;
import com.likjelion.besession.week09_domain.analysis.dto.response.AnalysisUploadResponse;
import com.likjelion.besession.week09_domain.analysis.entity.Analysis;
import com.likjelion.besession.week09_domain.analysis.repository.AnalysisRepository;
import com.likjelion.besession.week09_domain.contract.entity.Contract;
import com.likjelion.besession.week09_domain.contract.repository.ContractRepository;
import com.likjelion.besession.week09_domain.report.entity.Report;
import com.likjelion.besession.week09_domain.report.repository.ReportRepository;
import com.likjelion.besession.week09_domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisService {

    private static final String DUMMY_CONTRACT_CONTENT = "임차인이 2개월 이상 차임을 연체할 경우 임대인은 즉시 계약을 해지할 수 있다.";
    private static final String DUMMY_AI_COMMENT = "월세를 2달 이상 내지 않으면 집주인이 계약을 해지할 수 있다는 뜻입니다.";
    private static final String DUMMY_WARNING = "표준 범위 안의 조항이지만, 월세 납부일과 납부 방법을 명확히 확인하는 것이 좋습니다.";

    private final ContractRepository contractRepository;
    private final AnalysisRepository analysisRepository;
    private final ReportRepository reportRepository;
    private final S3Service s3Service;

    @Transactional
    public AnalysisUploadResponse uploadAndAnalyze(Long contractId, MultipartFile file, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        String imageUrl;
        try {
            imageUrl = s3Service.upload(file, "contracts");
        } catch (IOException e) {
            throw new CustomException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }

        Report report = reportRepository.save(Report.builder()
                .title("계약서 분석 리포트")
                .content("분석이 완료되었습니다.")
                .user(user)
                .property(contract.getProperty())
                .deleted_at(LocalDateTime.of(9999, 12, 31, 0, 0))
                .build());

        Analysis analysis = analysisRepository.save(Analysis.builder()
                .contractImageUrl(imageUrl)
                .contractContent(DUMMY_CONTRACT_CONTENT)
                .aiComment(DUMMY_AI_COMMENT)
                .warning(DUMMY_WARNING)
                .report(report)
                .contract(contract)
                .build());

        return AnalysisUploadResponse.builder()
                .analysisId(analysis.getId())
                .contractId(contractId)
                .status("COMPLETED")
                .createdAt(analysis.getCreatedAt())
                .build();
    }

    public AnalysisResultResponse getAnalysis(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        Analysis analysis = analysisRepository.findByContract(contract)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        return AnalysisResultResponse.builder()
                .analysisId(analysis.getId())
                .contractId(contractId)
                .contractContent(analysis.getContractContent())
                .aiComment(analysis.getAiComment())
                .warning(analysis.getWarning())
                .createdAt(analysis.getCreatedAt())
                .build();
    }
}
