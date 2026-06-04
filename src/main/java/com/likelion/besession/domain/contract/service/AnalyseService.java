package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.contract.dto.request.CreateAnalyseRequest;
import com.likelion.besession.domain.contract.dto.response.AnalyseResponse;
import com.likelion.besession.domain.contract.entity.Analyse;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.contract.exception.AnalyseErrorCode;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.AnalyseRepository;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyseService {

    private final AnalyseRepository analyseRepository;
    private final UserRepository userRepository;

    @Transactional
    public AnalyseResponse createAnalyse(CreateAnalyseRequest request) {
        log.info("[AnalyseService] 계약서 분석 요청 - userId: {}", request.getUserId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ContractErrorCode.USER_NOT_FOUND));

        String mockOriginalText = "임차인이 2개월이상 차임을 연체할 경우 임대인은 즉시 계약을 해지할 수 있다.";
        String mockAiInterpretation = "월세를 2달 이상 못 내면 집주인이 바로 계약을 끊을 수 있다는 뜻이에요. 법적으로도 2개월 이상 연체 시 해지 가능해서 이것은 표준 조항이에요.";
        String mockWarningText = "표준 범위 안의 조항이에요. 다만 월세 납부일과 방법(계좌이체 등)을 특약에 명시해두면 나중에 분쟁을 예방할 수 있어요!";

        Analyse analyse = Analyse.builder()
                .user(user)
                .imageUrl(request.getImageUrl())
                .originalText(mockOriginalText)
                .aiInterpretation(mockAiInterpretation)
                .warningText(mockWarningText)
                .build();

        Analyse savedAnalyse = analyseRepository.save(analyse);
        return toAnalyseResponse(savedAnalyse);
    }

    @Transactional(readOnly = true)
    public List<AnalyseResponse> getMyAnalyses(Long userId) {
        List<Analyse> analyseList = analyseRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return analyseList.stream().map(this::toAnalyseResponse).toList();
    }

    @Transactional(readOnly = true)
    public AnalyseResponse getAnalyseById(Long analyseId) {
        Analyse analyse = analyseRepository.findById(analyseId)
                .orElseThrow(() -> new CustomException(AnalyseErrorCode.ANALYSE_NOT_FOUND));
        return toAnalyseResponse(analyse);
    }

    private AnalyseResponse toAnalyseResponse(Analyse analyse) {
        return AnalyseResponse.builder()
                .analyseId(analyse.getId())
                .imageUrl(analyse.getImageUrl())
                .originalText(analyse.getOriginalText())
                .aiInterpretation(analyse.getAiInterpretation())
                .warningText(analyse.getWarningText())
                .createdAt(analyse.getCreatedAt())
                .build();
    }
}