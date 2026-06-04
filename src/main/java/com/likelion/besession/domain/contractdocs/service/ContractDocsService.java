package com.likelion.besession.domain.contractdocs.service;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.contractdocs.dto.request.ContractDocsAnalyzeRequest;
import com.likelion.besession.domain.contractdocs.dto.response.ContractDocsAnalyzeResponse;
import com.likelion.besession.domain.contractdocs.dto.response.ContractDocsListResponse;
import com.likelion.besession.domain.contractdocs.entity.ContractDocs;
import com.likelion.besession.domain.contractdocs.exception.ContractDocsErrorCode;
import com.likelion.besession.domain.contractdocs.repository.ContractDocsRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ContractDocsService {

    private final ContractDocsRepository contractDocsRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    private ContractDocsAnalyzeResponse toResponse(ContractDocs contractDocs) {
        return ContractDocsAnalyzeResponse.builder()
                .contractDocsId(contractDocs.getContractDocsId())
                .contractId(contractDocs.getContract().getContractId())
                .title(contractDocs.getTitle())
                .contractImage(contractDocs.getContractImage())
                .content(contractDocs.getContent())
                .aiTranslation(contractDocs.getAiTranslation())
                .note(contractDocs.getNote())
                .createdAt(contractDocs.getCreatedAt())
                .build();
    }

    private ContractDocsListResponse toListResponse(ContractDocs contractDocs) {
        return ContractDocsListResponse.builder()
                .contractDocsId(contractDocs.getContractDocsId())
                .contractId(contractDocs.getContract().getContractId())
                .title(contractDocs.getTitle())
                .createdAt(contractDocs.getCreatedAt())
                .build();
    }

    // 계약서 분석
    @Transactional
    public ContractDocsAnalyzeResponse analyzeContractDocs(Long userId, Long contractId, ContractDocsAnalyzeRequest request) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        // 이미 계약서가 존재하면 중복 생성 방지
        if (contractDocsRepository.findByContract(contract).isPresent()) {
            log.warn("[ContractDocsService] 계약서가 이미 존재합니다: title: {}", request.getTitle());
            throw new CustomException(ContractDocsErrorCode.CONTRACT_DOCS_ALREADY_EXISTS);
        }

        // 계약서 분석 객체 생성
        ContractDocs contractDocs = ContractDocs.builder()
                .contract(contract)
                .title(request.getTitle())
                .contractImage(request.getContractImage())

                // AI 분석 로직이 존재하지 않기 때문에 하드코딩으로 대체
                .content("임차인이 2개월 이상 차임을 연체할 경우 임대인은 즉시 계약을 해지할 수 있어요.")
                .aiTranslation("월세를 2달 못 내면 집주인이 바로 계약을 끊을 수 있다는 뜻이에요.")
                .note("월세 납부일과 방법을 특약에 명시해두면 나중에 분쟁을 예방할 수 있어요.")
                .build();

        // DB에 저장
        ContractDocs savedDocs = contractDocsRepository.save(contractDocs);

        log.info("[ContractDocsService] 계약서 생성 완료: contractDocsId= {}, contractId= {}", savedDocs.getContractDocsId(), contractId);

        return toResponse(savedDocs);
    }

    // 계약서 분석 결과 단건 조회
    public ContractDocsAnalyzeResponse lookContractDocs(Long userId, Long contractId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        // 계약서 분석 결과가 존재하는지 확인
        ContractDocs contractDocs = contractDocsRepository.findByContract(contract)
                .orElseThrow(() -> new CustomException(ContractDocsErrorCode.CONTRACT_DOCS_NOT_FOUND));

        return toResponse(contractDocs);
    }

    // 내 계약서 분석 결과 전체 목록 조회 (마이페이지)
    public List<ContractDocsListResponse> myContractDocs(Long userId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return contractDocsRepository.findAllByContractUser(user)
                .stream()
                .map(this::toListResponse)
                .toList();
    }

    // 계약서 분석 결과 삭제
    @Transactional
    public void deleteContractDocs(Long userId, Long contractId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        // 계약서 분석 결과가 존재하는지 확인
        ContractDocs contractDocs = contractDocsRepository.findByContract(contract)
                .orElseThrow(() -> new CustomException(ContractDocsErrorCode.CONTRACT_DOCS_NOT_FOUND));

        // DB에서 삭제
        contractDocsRepository.delete(contractDocs);

        log.info("[ContractDocsService] 계약서 삭제 완료: contractId= {}", contractId);
    }
}
