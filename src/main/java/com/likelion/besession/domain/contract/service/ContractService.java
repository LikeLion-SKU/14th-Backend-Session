package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.checklist.service.ChecklistService;
import com.likelion.besession.domain.contract.dto.ContractResponse;
import com.likelion.besession.domain.contract.dto.ContractSaveRequest;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ChecklistService checklistService;

    @Transactional(readOnly = true)
    public ContractResponse getMyContract(User user) {
        Contract contract = contractRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        return ContractResponse.from(contract);
    }

    @Transactional
    public void saveMyContract(User user, ContractSaveRequest request) {
        Contract contract = contractRepository.findByUser(user)
                .orElse(null);

        if (contract == null) {
            Contract newContract = Contract.builder()
                    .user(user)
                    .name(request.getName())
                    .address(request.getAddress())
                    .contractSource(request.getContractSource())
                    .contractStep(request.getContractStep())
                    .progressRate(request.getProgressRate())
                    .build();

            contractRepository.save(newContract);

            checklistService.createDefaultChecklists(newContract);
            return;
        }

        contract.update(
                request.getName(),
                request.getAddress(),
                request.getContractSource(),
                request.getContractStep(),
                request.getProgressRate()
        );
    }
}