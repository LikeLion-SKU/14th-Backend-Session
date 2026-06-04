package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.contract.dto.request.CreateContractRequest;
import com.likelion.besession.domain.contract.dto.response.*;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractAnalysis;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractAnalysisRepository;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractAnalysisRepository contractAnalysisRepository;
    private final UserRepository userRepository;

    // 계약서 생성: 사용자와 요청 정보를 바탕으로 계약서 등록
    public ContractResponse createContract(Long userId, CreateContractRequest request) {
        User user = getUser(userId);
        Contract contract = Contract.builder()
                .user(user)
                .contractPartnerName(request.getContractPartnerName())
                .contractDoc(request.getContractDoc())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        return ContractResponse.from(contractRepository.save(contract));
    }

    // 내 계약서 목록 조회: 생성일 최신순 정렬
    @Transactional(readOnly = true)
    public List<ContractResponse> getMyContracts(Long userId) {
        User user = getUser(userId);
        return contractRepository.findAllByUserOrderByCreatedDateDesc(user)
                .stream()
                .map(ContractResponse::from)
                .toList();
    }

    // 계약서 상세 조회: 본인 소유 계약서만 접근 가능
    @Transactional(readOnly = true)
    public ContractResponse getContract(Long userId, Long contractId) {
        Contract contract = getContractByIdAndUser(userId, contractId);
        return ContractResponse.from(contract);
    }

    // 계약서 내용 조회: 파일 URL과 계약 기간 반환
    @Transactional(readOnly = true)
    public ContractContentResponse getContractContent(Long userId, Long contractId) {
        Contract contract = getContractByIdAndUser(userId, contractId);
        return ContractContentResponse.from(contract);
    }

    // 계약 당사자 조회: 계약자(본인)와 상대방 정보 반환
    @Transactional(readOnly = true)
    public ContractPartiesResponse getContractParties(Long userId, Long contractId) {
        Contract contract = getContractByIdAndUser(userId, contractId);
        return ContractPartiesResponse.from(contract);
    }

    // AI 분석 요청: 중복 분석 방지 후 ContractAnalysis 저장
    // 실제 AI 서비스 연동 시 stub 내용을 AI API 호출로 교체
    public ContractAnalysisResponse analyzeContract(Long userId, Long contractId) {
        Contract contract = getContractByIdAndUser(userId, contractId);

        // 이미 분석된 계약서는 재분석 불가
        if (contractAnalysisRepository.existsByContract(contract)) {
            throw new CustomException(ContractErrorCode.ALREADY_ANALYZED);
        }

        // AI 분석 stub - 실제 AI 연동 시 이 부분을 교체
        ContractAnalysis analysis = ContractAnalysis.builder()
                .contract(contract)
                .aiSummary("계약서 AI 분석 요약: 본 계약은 임대차 계약으로 주요 조항을 분석한 결과입니다.")
                .aiContents("계약 내용 분석: 계약 기간, 임대료, 보증금 조항 등이 포함되어 있습니다.")
                .aiIssues("주의 사항: 1. 특약 사항 확인 필요, 2. 계약 해지 조건 검토 권장")
                .aiScore(35)
                .build();

        // 분석 완료 표시
        contract.markAnalyzed();
        return ContractAnalysisResponse.from(contractAnalysisRepository.save(analysis));
    }

    // AI 분석 결과 조회: 분석이 완료되지 않은 경우 예외 처리
    @Transactional(readOnly = true)
    public ContractAnalysisResponse getAnalysis(Long userId, Long contractId) {
        Contract contract = getContractByIdAndUser(userId, contractId);
        ContractAnalysis analysis = contractAnalysisRepository.findByContract(contract)
                .orElseThrow(() -> new CustomException(ContractErrorCode.ANALYSIS_NOT_FOUND));
        return ContractAnalysisResponse.from(analysis);
    }

    // 계약서 이미지 조회: contractDoc URL 반환 (내용 조회와 동일한 데이터)
    @Transactional(readOnly = true)
    public ContractContentResponse getContractImage(Long userId, Long contractId) {
        Contract contract = getContractByIdAndUser(userId, contractId);
        return ContractContentResponse.from(contract);
    }

    // 계약서 조회 공통 메서드: 본인 소유 여부 검증 포함
    private Contract getContractByIdAndUser(Long userId, Long contractId) {
        User user = getUser(userId);
        return contractRepository.findByIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
