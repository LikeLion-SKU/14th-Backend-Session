package com.likjelion.besession.week09_domain.checklist.service;

import com.likjelion.besession.global.exception.CustomException;
import com.likjelion.besession.global.exception.GlobalErrorCode;
import com.likjelion.besession.week09_domain.checklist.dto.request.UpdateChecklistRequest;
import com.likjelion.besession.week09_domain.checklist.dto.response.ChecklistItemResponse;
import com.likjelion.besession.week09_domain.checklist.dto.response.ChecklistUpdateResponse;
import com.likjelion.besession.week09_domain.checklist.entity.ContractCheckList;
import com.likjelion.besession.week09_domain.checklist.repository.ContractCheckListRepository;
import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import com.likjelion.besession.week09_domain.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChecklistService {

    private final ContractCheckListRepository contractCheckListRepository;
    private final ContractRepository contractRepository;

    public List<ChecklistItemResponse> getChecklists(Long contractId, ContractStatus status) {
        List<ContractCheckList> list = (status != null)
                ? contractCheckListRepository.findAllByContractIdAndContractStatus(contractId, status)
                : contractCheckListRepository.findAllByContractId(contractId);

        return list.stream()
                .map(ccl -> ChecklistItemResponse.builder()
                        .contractChecklistId(ccl.getId())
                        .checklistId(ccl.getCheckList().getId())
                        .title(ccl.getCheckList().getTitle())
                        .content(ccl.getCheckList().getContent())
                        .contractStatus(ccl.getContractStatus())
                        .checked(ccl.isChecked())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public ChecklistUpdateResponse updateChecked(Long contractChecklistId, UpdateChecklistRequest request) {
        ContractCheckList ccl = contractCheckListRepository.findById(contractChecklistId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        contractCheckListRepository.updateChecked(contractChecklistId, request.isChecked());

        Long contractId = ccl.getContract().getId();
        long total = contractCheckListRepository.countByContractId(contractId);
        long checkedAfter = contractCheckListRepository.countByContractIdAndCheckedTrue(contractId);
        int processRate = (total > 0) ? (int) ((double) checkedAfter / total * 100) : 0;
        contractRepository.updateProcessRate(contractId, processRate);

        return ChecklistUpdateResponse.builder()
                .contractChecklistId(contractChecklistId)
                .checked(request.isChecked())
                .updatedAt(LocalDateTime.now())
                .contractId(contractId)
                .processRate(processRate)
                .build();
    }
}
