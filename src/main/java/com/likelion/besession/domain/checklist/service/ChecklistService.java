package com.likelion.besession.domain.checklist.service;

import com.likelion.besession.domain.checklist.dto.request.CreateChecklistRequest;
import com.likelion.besession.domain.checklist.dto.request.UpdateChecklistRequest;
import com.likelion.besession.domain.checklist.dto.response.ChecklistResponse;
import com.likelion.besession.domain.checklist.entity.Checklist;
import com.likelion.besession.domain.checklist.exception.ChecklistErrorCode;
import com.likelion.besession.domain.checklist.repository.ChecklistRepository;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractStatus;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ContractRepository contractRepository;

    public List<ChecklistResponse> getChecklists(Long userId, Long contractId, String stage) {
        Contract contract = findContractById(contractId);
        validateOwner(contract, userId);

        ContractStatus contractStatus = parseStage(stage);
        List<Checklist> checklists = checklistRepository.findByContractIdAndStageOrderByChecklistOrderAsc(contractId, contractStatus);

        return checklists.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ChecklistResponse createChecklist(Long userId, Long contractId, CreateChecklistRequest request) {
        Contract contract = findContractById(contractId);
        validateOwner(contract, userId);

        Checklist checklist = Checklist.builder()
                .contract(contract)
                .stage(parseStage(request.stage()))
                .taskname(request.taskName())
                .description(request.description())
                .checklistOrder(request.checklistOrder())
                .build();

        Checklist saved = checklistRepository.save(checklist);

        return toResponse(saved);
    }

    @Transactional
    public ChecklistResponse updateChecklist(Long userId, Long taskId, UpdateChecklistRequest request) {
        Checklist checklist = findChecklistById(taskId);
        validateOwner(checklist.getContract(), userId);

        checklist.toggleChecked(request.isChecked());

        return toResponse(checklist);
    }

    @Transactional
    public void deleteChecklist(Long userId, Long taskId) {
        Checklist checklist = findChecklistById(taskId);
        validateOwner(checklist.getContract(), userId);

        checklistRepository.delete(checklist);
    }

    private Contract findContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));
    }

    private Checklist findChecklistById(Long taskId) {
        return checklistRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));
    }

    private void validateOwner(Contract contract, Long userId) {
        if (!contract.getUser().getId().equals(userId)) {
            throw new CustomException(ChecklistErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    private ContractStatus parseStage(String stage) {
        try {
            return ContractStatus.valueOf(stage);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ChecklistErrorCode.INVALID_STAGE);
        }
    }

    private ChecklistResponse toResponse(Checklist checklist) {
        return ChecklistResponse.builder()
                .taskId(checklist.getId())
                .stage(checklist.getStage().name())
                .taskName(checklist.getTaskname())
                .description(checklist.getDescription())
                .checklistOrder(checklist.getChecklistOrder())
                .isChecked(checklist.getIsChecked())
                .createdAt(checklist.getCreatedAt())
                .build();
    }
}
