package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.contract.dto.response.ContractCreateResponse;
import com.likelion.besession.domain.contract.dto.response.ContractListResponse;
import com.likelion.besession.domain.contract.dto.response.ContractTypeResponse;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
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
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    private ContractCreateResponse toResponse(Contract contract) {
        return ContractCreateResponse.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUser().getUserId())
                .contractStatus(contract.getContractStatus())
                .createdAt(contract.getCreatedAt())
                .build();
    }

    private ContractListResponse toListResponse(Contract contract) {
        return ContractListResponse.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUser().getUserId())
                .createdAt(contract.getCreatedAt())
                .build();
    }

    private ContractTypeResponse toTypeResponse(Contract contract) {
        return ContractTypeResponse.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUser().getUserId())
                .contractStatus(contract.getContractStatus())
                .createdAt(contract.getCreatedAt())
                .build();
    }

    // 계약 생성
    @Transactional
    public ContractCreateResponse createContract(Long userId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약 객체 생성
        Contract contract = Contract.builder()
                .user(user)
                .build();

        // DB에 저장
        Contract savedContract = contractRepository.save(contract);

        // 로그 출력
        log.info("[ContractService] 계약 생성 완료: contractId= {}, userId= {}", savedContract.getContractId(), user.getUserId());

        return toResponse(savedContract);
    }

    // 자기 자신의 계약 전체 목록 조회
    public List<ContractListResponse> allContract(Long userId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return contractRepository.findAllByUser(user)
                .stream()
                .map(this::toListResponse)
                .toList();
    }

    // 계약 단건 조회
    public ContractTypeResponse lookContract(Long userId, Long contractId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        return toTypeResponse(contract);
    }

    // 계약 삭제
    @Transactional
    public void deleteContract(Long userId, Long contractId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        // DB에서 삭제
        contractRepository.delete(contract);

        // 로그 출력
        log.info("[ContractService] 계약 삭제 성공: contractId= {}", contractId);
    }
}
