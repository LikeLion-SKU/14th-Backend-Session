package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.contract.dto.request.ChecklistStatusRequest;
import com.likelion.besession.domain.contract.dto.request.ContractCreateRequest;
import com.likelion.besession.domain.contract.dto.response.ChecklistResponse;
import com.likelion.besession.domain.contract.dto.response.ContractResponse;
import com.likelion.besession.domain.contract.entity.Checklist;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractPhase;
import com.likelion.besession.domain.contract.exception.ChecklistNotFoundException;
import com.likelion.besession.domain.contract.exception.ContractNotFoundException;
import com.likelion.besession.domain.contract.repository.ChecklistRepository;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserNotFoundException;
import com.likelion.besession.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final ChecklistRepository checklistRepository;
    private final UserRepository userRepository;

    @Transactional
    public ContractResponse createContract(ContractCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(UserNotFoundException::new);

        Contract contract = Contract.builder()
            .user(user)
            .content(request.getContent())
            .address(request.getAddress())
            .build();

        return ContractResponse.from(contractRepository.save(contract));
    }

    public ContractResponse getContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(ContractNotFoundException::new);
        return ContractResponse.from(contract);
    }

    public List<ChecklistResponse> getChecklists(Long contractId, ContractPhase phase) {
        contractRepository.findById(contractId)
            .orElseThrow(ContractNotFoundException::new);

        List<Checklist> checklists = (phase == null)
            ? checklistRepository.findByContractContractId(contractId)
            : checklistRepository.findByContractContractIdAndPhase(contractId, phase);

        return checklists.stream()
            .map(ChecklistResponse::from)
            .toList();
    }

    @Transactional
    public ChecklistResponse updateChecklistStatus(Long contractId, Long checklistId,
        ChecklistStatusRequest request) {
        contractRepository.findById(contractId)
            .orElseThrow(ContractNotFoundException::new);

        Checklist checklist = checklistRepository.findById(checklistId)
            .orElseThrow(ChecklistNotFoundException::new);

        checklist.updateStatus(request.isStatus());
        return ChecklistResponse.from(checklist);
    }
}
