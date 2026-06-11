package com.likelion.besession.domain_ia.contract.service;

import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain_ia.checklist.entity.Checklist;
import com.likelion.besession.domain_ia.checklist.entity.ContractChecklistStatus;
import com.likelion.besession.domain_ia.checklist.repository.ChecklistRepository;
import com.likelion.besession.domain_ia.checklist.repository.ContractChecklistStatusRepository;
import com.likelion.besession.domain_ia.contract.dto.request.ContractCreateRequest;
import com.likelion.besession.domain_ia.contract.dto.response.ContractAnalyzationResponse;
import com.likelion.besession.domain_ia.contract.dto.response.ContractCreateResponse;
import com.likelion.besession.domain_ia.contract.dto.response.ContractDetailResponse;
import com.likelion.besession.domain_ia.contract.entity.Contract;
import com.likelion.besession.domain_ia.contract.entity.ContractAnalyzation;
import com.likelion.besession.domain_ia.contract.entity.Process;
import com.likelion.besession.domain_ia.contract.repository.ContractAnalyzationRepository;
import com.likelion.besession.domain_ia.contract.repository.ContractRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DialectOverride;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractAnalyzationRepository contractAnalyzationRepository;
    private final UserRepository userRepository;
    private final ChecklistRepository checklistRepository;
    private final ContractChecklistStatusRepository contractChecklistStatusRepository;

    // 계약서 생성
    public ContractCreateResponse createContract(ContractCreateRequest contractCreateRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Contract contract = Contract.builder()
                .address(contractCreateRequest.getAddress())
                .contractImageURL(contractCreateRequest.getContractImageURL())
                .user(user)
                .build();

        Contract createdContract = contractRepository.save(contract);

        makeDefaultChecklistStatus(createdContract);

        ContractCreateResponse contractCreateResponse = toContractCreateResponse(createdContract);

        return contractCreateResponse;
    }

    // 계약서 조회
    public ContractDetailResponse getContractDetail(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return toContractDetailResponse(contract);
    }

    // 현재 진행중 계약 조회
    public ContractDetailResponse getContractDetailOnGoing(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Contract contract = contractRepository.findByUserAndIsDoneFalse(user);

        return toContractDetailResponse(contract);
    }

    // 유저 계약서 리스트 반환
    public List<ContractDetailResponse> getUserContracts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        List<Contract> contracts = contractRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<ContractDetailResponse> contractDetailResponseList = new ArrayList<>();

        for (Contract contract : contracts) {
            contractDetailResponseList.add(toContractDetailResponse(contract));
        }

        return contractDetailResponseList;
    }

    // 계약서 상세 분석 반환
    public ContractAnalyzationResponse getContractAnalyzation(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(()-> new CustomException(UserErrorCode.USER_DUPLICATED_EMAIL));

        ContractAnalyzation contractAnalyzation = contractAnalyzationRepository.findByContract(contract);

        return toContractAnalyzationResponse(contractAnalyzation);
    }

    public ContractCreateResponse toContractCreateResponse(Contract contract) {
        return ContractCreateResponse.builder()
                .contractId(contract.getId())
                .address(contract.getAddress())
                .userId(contract.getUser().getId())
                .contractImageURL(contract.getContractImageURL())
                .currentProcess(contract.getCurrentProcess())
                .isDone(contract.isDone())
                .build();
    }

    public ContractDetailResponse toContractDetailResponse(Contract contract) {
        return ContractDetailResponse.builder()
                .contractId(contract.getId())
                .address(contract.getAddress())
                .userId(contract.getUser().getId())
                .contractImageURL(contract.getContractImageURL())
                .currentProcess(contract.getCurrentProcess())
                .isDone(contract.isDone())
                .build();
    }

    public ContractAnalyzationResponse toContractAnalyzationResponse(ContractAnalyzation contractAnalyzation) {
        return ContractAnalyzationResponse.builder()
                .contractId(contractAnalyzation.getContract().getId())
                .content(contractAnalyzation.getContent())
                .aiAnalyzation(contractAnalyzation.getAiAnalyzation())
                .notice(contractAnalyzation.getNotice())
                .build();
    }

    // 기본 계약서 체크리스트 상태 생성(테스트용 로직)
    public void makeDefaultChecklistStatus(Contract contract){
        List<ContractChecklistStatus> contractChecklistStatusList = new ArrayList<>();

        for(Long i = 1L; i <= 6L; i++){
            Checklist checklist = checklistRepository.findById(i).orElseThrow();

            ContractChecklistStatus contractChecklistStatus =
                    ContractChecklistStatus.builder()
                            .checklist(checklist)
                            .contract(contract)
                            .process(checklist.getProcess())
                            .build();

            contractChecklistStatusRepository.save(contractChecklistStatus);
        }
    }
}