package com.example.week09likelion.domain.contract.service;

import com.example.week09likelion.domain.contract.dto.request.ContractRequest;
import com.example.week09likelion.domain.contract.dto.response.ContractResponse;
import com.example.week09likelion.domain.contract.entity.Contract;
import com.example.week09likelion.domain.contract.exception.ContractErrorCode;
import com.example.week09likelion.domain.contract.repository.ContractRepository;
import com.example.week09likelion.domain.user.entity.User;
import com.example.week09likelion.domain.user.exception.UserErrorCode;
import com.example.week09likelion.domain.user.repository.UserRepository;
import com.example.week09likelion.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import  com.example.week09likelion.domain.schedule.repository.ContractScheduleRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final ContractScheduleRepository contractScheduleRepository;

    //  내 계약 조회
    public ContractResponse getMyContract(Long userId) {
        Contract contract = contractRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        return ContractResponse.from(contract);
    }

    //  계약 등록
    @Transactional
    public ContractResponse createContract(Long userId, ContractRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        if (contractRepository.existsByUserId(userId)) {
            throw new CustomException(ContractErrorCode.DUPLICATE_CONTRACT);
        }

        Contract contract = Contract.builder()
                .user(user)
                .title(request.getTitle())
                .address(request.getAddress())
                .status(request.getStatus())
                .progress(request.getProgress())
                .build();

        Contract savedContract = contractRepository.save(contract);

        return ContractResponse.from(savedContract);
    }

    // 내 계약 수정
    @Transactional
    public ContractResponse updateMyContract(Long userId, ContractRequest request) {
        Contract contract = contractRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        contract.updateContract(
                request.getTitle(),
                request.getAddress(),
                request.getStatus(),
                request.getProgress()
        );

        return ContractResponse.from(contract);
    }

    // 내 계약 삭제
    @Transactional
    public void deleteMyContract(Long userId) {
        Contract contract = contractRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        contractScheduleRepository.deleteByContractId(contract.getId());

        contractRepository.delete(contract);
    }
}