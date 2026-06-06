package com.likelion.besession.domain.analysis.service;

import com.likelion.besession.domain.analysis.dto.response.AnalysisResponse;
import com.likelion.besession.domain.analysis.dto.response.AnalysisSummaryResponse;
import com.likelion.besession.domain.analysis.entity.Analysis;
import com.likelion.besession.domain.analysis.exception.AnalysisErrorCode;
import com.likelion.besession.domain.analysis.repository.AnalysisRepository;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final ContractRepository contractRepository;

    @Transactional
    public AnalysisResponse createAnalysis(Long userId, Long contractId, MultipartFile image) {
        Contract contract = findContractById(contractId);
        validateOwner(contract, userId);

        // TODO: 이미지 저장 및 AI 분석 로직 구현
        String imageUrl = "uploaded/" + image.getOriginalFilename();

        Analysis analysis = Analysis.builder()
                .contract(contract)
                .imageUrl(imageUrl)
                .build();

        Analysis saved = analysisRepository.save(analysis);

        return toResponse(saved);
    }

    public List<AnalysisSummaryResponse> getAnalyses(Long userId, Long contractId) {
        Contract contract = findContractById(contractId);
        validateOwner(contract, userId);

        List<Analysis> analyses = analysisRepository.findByContractIdOrderByCreatedAtDesc(contractId);

        return analyses.stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    public AnalysisResponse getAnalysis(Long userId, Long analysisId) {
        Analysis analysis = findAnalysisById(analysisId);
        validateOwner(analysis.getContract(), userId);

        return toResponse(analysis);
    }

    @Transactional
    public void deleteAnalysis(Long userId, Long analysisId) {
        Analysis analysis = findAnalysisById(analysisId);
        validateOwner(analysis.getContract(), userId);

        analysisRepository.delete(analysis);
    }

    private Contract findContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));
    }

    private Analysis findAnalysisById(Long analysisId) {
        return analysisRepository.findById(analysisId)
                .orElseThrow(() -> new CustomException(AnalysisErrorCode.ANALYSIS_NOT_FOUND));
    }

    private void validateOwner(Contract contract, Long userId) {
        if (!contract.getUser().getId().equals(userId)) {
            throw new CustomException(AnalysisErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    private AnalysisResponse toResponse(Analysis analysis) {
        return AnalysisResponse.builder()
                .analysisId(analysis.getId())
                .contractContent(analysis.getContractContent())
                .aiInterpretation(analysis.getAiInterpretation())
                .precautions(analysis.getPrecautions())
                .imageUrl(analysis.getImageUrl())
                .createdAt(analysis.getCreatedAt())
                .build();
    }

    private AnalysisSummaryResponse toSummaryResponse(Analysis analysis) {
        return AnalysisSummaryResponse.builder()
                .analysisId(analysis.getId())
                .imageUrl(analysis.getImageUrl())
                .createdAt(analysis.getCreatedAt())
                .build();
    }
}
