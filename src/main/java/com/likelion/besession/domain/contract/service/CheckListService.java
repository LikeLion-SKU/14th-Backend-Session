package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.contract.dto.request.UpdateCheckListRequest;
import com.likelion.besession.domain.contract.dto.response.CheckListResponse;
import com.likelion.besession.domain.contract.entity.CheckList;
import com.likelion.besession.domain.contract.entity.Stage;
import com.likelion.besession.domain.contract.exception.CheckListErrorCode;
import com.likelion.besession.domain.contract.repository.CheckListRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListRepository checkListRepository;

    @Transactional(readOnly = true)
    public List<CheckListResponse> getCheckLists(Long contractId, Stage stage) {
        log.debug("[CheckListService] 체크리스트 목록 조회 - contractId: {}, stage: {}", contractId, stage);

        List<CheckList> checkLists = checkListRepository.findByContractIdAndStage(contractId, stage);
        return checkLists.stream().map(this::toCheckListResponse).toList();
    }

    @Transactional
    public CheckListResponse updateCheckListStatus(Long checkListId, UpdateCheckListRequest request) {
        log.info("[CheckListService] 체크리스트 상태 변경 - checkListId: {}, isChecked: {}", checkListId, request.getIsChecked());

        CheckList checkList = checkListRepository.findById(checkListId)
                .orElseThrow(() -> new CustomException(CheckListErrorCode.CHECKLIST_NOT_FOUND));

        checkList.updateStatus(request.getIsChecked());
        checkListRepository.save(checkList);

        return toCheckListResponse(checkList);
    }

    private CheckListResponse toCheckListResponse(CheckList checkList) {
        return CheckListResponse.builder()
                .checkListId(checkList.getId())
                .stage(checkList.getStage())
                .title(checkList.getTitle())
                .verificationMethod(checkList.getVerificationMethod())
                .verificationUrl(checkList.getVerificationUrl())
                .warningNote(checkList.getWarningNote())
                .isChecked(checkList.isChecked())
                .build();
    }
}