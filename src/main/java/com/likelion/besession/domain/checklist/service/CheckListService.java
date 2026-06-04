package com.likelion.besession.domain.checklist.service;

import com.likelion.besession.domain.checklist.dto.response.CheckListResponse;
import com.likelion.besession.domain.checklist.entity.CheckList;
import com.likelion.besession.domain.checklist.exception.CheckListErrorCode;
import com.likelion.besession.domain.checklist.repository.CheckListRepository;
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
public class CheckListService {

    private final CheckListRepository checkListItemRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    private CheckListResponse toResponse(CheckList checkListItem) {
        return CheckListResponse.builder()
                .checkListId(checkListItem.getCheckListId())
                .contractId(checkListItem.getContract().getContractId())
                .phase(checkListItem.getPhase())
                .title(checkListItem.getCheckListTitle())
                .checkListContent(checkListItem.getCheckListContent())
                .checkListStatus(checkListItem.getCheckListStatus())
                .createdAt(checkListItem.getCreatedAt())
                .build();
    }

    // 현재 단계 체크리스트 조회
    public List<CheckListResponse> checkListLook(Long userId, Long contractId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        return checkListItemRepository.findAllByContractAndPhase(contract, contract.getContractStatus())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // 체크리스트 완료 처리
    @Transactional
    public CheckListResponse completeCheckList(Long userId, Long checkListId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 체크리스트가 존재하는지 확인
        CheckList checkList = checkListItemRepository.findById(checkListId)
                .orElseThrow(() -> new CustomException(CheckListErrorCode.CHECK_LIST_NOT_FOUND));

        /*
        if (!checkListItem.getContract().getUser().getUserId().equals(userId)) {
            throw new CustomException(CheckListErrorCode.CHECK_LIST_FORBIDDEN);
        }
         */

        // DB에 저장
        checkList.complete();

        log.info("[CheckListService] 체크리스트 완료: checkListId= {}", checkListId);

        return toResponse(checkList);
    }

    // 체크리스트 완료 취소
    @Transactional
    public CheckListResponse incompleteCheckList(Long userId, Long checkListId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 체크리스트가 존재하는지 확인
        CheckList checkList = checkListItemRepository.findById(checkListId)
                .orElseThrow(() -> new CustomException(CheckListErrorCode.CHECK_LIST_NOT_FOUND));

        /*
        if (!checkListItem.getContract().getUser().getUserId().equals(userId)) {
            throw new CustomException(CheckListErrorCode.CHECK_LIST_FORBIDDEN);
        }
         */

        // DB에 저장
        checkList.incomplete();

        // 로그 출력
        log.info("[CheckListService] 체크리스트 완료 취소: checkListId= {}", checkListId);

        return toResponse(checkList);
    }

    // 체크리스트 삭제
    @Transactional
    public void deleteCheckList(Long userId, Long checkListId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 체크리스트가 존재하는지 확인
        CheckList checkListItem = checkListItemRepository.findById(checkListId)
                .orElseThrow(() -> new CustomException(CheckListErrorCode.CHECK_LIST_NOT_FOUND));
        /*
        if (!checkListItem.getContract().getUser().getUserId().equals(userId)) {
            throw new CustomException(CheckListErrorCode.CHECK_LIST_FORBIDDEN);
        }
         */

        // DB에서 제거
        checkListItemRepository.delete(checkListItem);

        // 로그 출력
        log.info("[CheckListService] 체크리스트 삭제 완료: checkListId= {}", checkListId);
    }
}
