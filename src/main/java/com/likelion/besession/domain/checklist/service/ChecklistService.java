package com.likelion.besession.domain.checklist.service;

import com.likelion.besession.domain.checklist.dto.ChecklistResponse;
import com.likelion.besession.domain.checklist.dto.ChecklistUpdateRequest;
import com.likelion.besession.domain.checklist.entity.Checklist;
import com.likelion.besession.domain.checklist.exception.ChecklistErrorCode;
import com.likelion.besession.domain.checklist.repository.ChecklistRepository;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ContractRepository contractRepository;

    @Transactional
    public void createDefaultChecklists(Contract contract) {
        List<Checklist> checklists = List.of(
                create(contract, "등기부등본 확인", "BEFORE_CONTRACT"),
                create(contract, "건축물대장 확인", "BEFORE_CONTRACT"),
                create(contract, "전세가율 확인", "BEFORE_CONTRACT"),
                create(contract, "공인중개사 정상 등록 확인", "BEFORE_CONTRACT"),

                create(contract, "계약서 특약사항 확인", "DURING_CONTRACT"),
                create(contract, "보증금 입금 계좌 확인", "DURING_CONTRACT"),

                create(contract, "전입신고 확인", "AFTER_CONTRACT"),
                create(contract, "확정일자 확인", "AFTER_CONTRACT")
        );

        checklistRepository.saveAll(checklists);
    }

    private Checklist create(Contract contract, String title, String contractStep) {
        return Checklist.builder()
                .contract(contract)
                .title(title)
                .contractStep(contractStep)
                .checked(false)
                .build();
    }

    @Transactional(readOnly = true)
    public List<ChecklistResponse> getMyChecklists(User user) {
        Contract contract = contractRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        return checklistRepository.findAllByContract(contract)
                .stream()
                .map(ChecklistResponse::from)
                .toList();
    }

    @Transactional
    public void updateChecklist(Long checklistId, ChecklistUpdateRequest request) {
        Checklist checklist = checklistRepository.findById(checklistId)
                .orElseThrow(() -> new CustomException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        checklist.updateChecked(request.getChecked());
    }
}