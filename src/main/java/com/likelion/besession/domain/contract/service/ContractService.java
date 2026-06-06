package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.contract.dto.request.CreateContractRequest;
import com.likelion.besession.domain.contract.dto.request.UpdateContractStatusRequest;
import com.likelion.besession.domain.contract.dto.response.ContractResponse;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractStatus;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    @Transactional
    public ContractResponse createContract(Long userId, CreateContractRequest request) {
        if (contractRepository.existsByUserId(userId)) {
            throw new CustomException(ContractErrorCode.CONTRACT_ALREADY_EXISTS);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Contract contract = Contract.builder()
                .user(user)
                .address(request.address())
                .build();

        Contract saved = contractRepository.save(contract);

        return toResponse(saved);
    }

    public ContractResponse getMyContract(Long userId) {
        Contract contract = contractRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        return toResponse(contract);
    }

    @Transactional
    public ContractResponse updateContractStatus(Long userId, Long contractId, UpdateContractStatusRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        if (!contract.getUser().getId().equals(userId)) {
            throw new CustomException(ContractErrorCode.UNAUTHORIZED_ACCESS);
        }

        try {
            ContractStatus status = ContractStatus.valueOf(request.status());
            contract.updateStatus(status);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ContractErrorCode.INVALID_STATUS);
        }

        return toResponse(contract);
    }

    private ContractResponse toResponse(Contract contract) {
        return ContractResponse.builder()
                .contractId(contract.getId())
                .address(contract.getAddress())
                .status(contract.getStatus().name())
                .createdAt(contract.getCreatedAt())
                .build();
    }
}
