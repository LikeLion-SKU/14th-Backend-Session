package com.likelion.besession.domain_ia.checklist.service;

import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain_ia.checklist.dto.request.ChecklistUpdateRequest;
import com.likelion.besession.domain_ia.checklist.dto.response.ChecklistDetailResponse;
import com.likelion.besession.domain_ia.checklist.entity.ContractChecklistStatus;
import com.likelion.besession.domain_ia.checklist.repository.ChecklistRepository;
import com.likelion.besession.domain_ia.checklist.repository.ContractChecklistStatusRepository;
import com.likelion.besession.domain_ia.contract.entity.Contract;
import com.likelion.besession.domain_ia.contract.entity.Process;
import com.likelion.besession.domain_ia.contract.repository.ContractRepository;
import com.likelion.besession.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ContractChecklistStatusRepository contractChecklistStatusRepository;
    private final ContractRepository contractRepository;

    public List<ChecklistDetailResponse> getChecklistDetails(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(()-> new CustomException(UserErrorCode.USER_NOT_FOUND)); // TODO 계약관련 에러코드 만들고, 대체하기

        Process process = contract.getCurrentProcess();

        List<ContractChecklistStatus> contractChecklistStatuses = contractChecklistStatusRepository.findAllByContractIdAndProcess(contractId, process);

        List<ChecklistDetailResponse> checklistDetailResponseList = new ArrayList<>();

        for (ContractChecklistStatus contractChecklistStatus : contractChecklistStatuses) {
            checklistDetailResponseList.add(toChecklistDetailResponse(contractChecklistStatus));
        }

        return checklistDetailResponseList;
    }

    // 체크리스트 업데이트
    public ChecklistDetailResponse updateChecklist(Long ContractChecklistStatusId, ChecklistUpdateRequest checklistUpdateRequest) {

        ContractChecklistStatus contractChecklistStatus = contractChecklistStatusRepository.findById(ContractChecklistStatusId).orElseThrow();

        contractChecklistStatus.setChecked(checklistUpdateRequest.isChecked());

        ContractChecklistStatus updatedContractChecklistStatus = contractChecklistStatusRepository.save(contractChecklistStatus);

        return ChecklistDetailResponse.builder()
                .contractChecklistStatusId(updatedContractChecklistStatus.getId())
                .checklistId(updatedContractChecklistStatus.getChecklist().getId())
                .name(updatedContractChecklistStatus.getChecklist().getName())
                .content(updatedContractChecklistStatus.getChecklist().getContent())
                .process(updatedContractChecklistStatus.getChecklist().getProcess())
                .build();
    }

    public ChecklistDetailResponse toChecklistDetailResponse(ContractChecklistStatus contractChecklistStatus){

        return ChecklistDetailResponse.builder()
                .contractChecklistStatusId(contractChecklistStatus.getId())
                .checklistId(contractChecklistStatus.getChecklist().getId())
                .name(contractChecklistStatus.getChecklist().getName())
                .content(contractChecklistStatus.getChecklist().getContent())
                .process(contractChecklistStatus.getChecklist().getProcess())
                .isChecked(contractChecklistStatus.isChecked())
                .build();
    }

}
