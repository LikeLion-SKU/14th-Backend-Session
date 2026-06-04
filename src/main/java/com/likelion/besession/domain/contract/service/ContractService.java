package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.checklist.entity.CheckList;
import com.likelion.besession.domain.checklist.entity.CheckListStatus;
import com.likelion.besession.domain.checklist.repository.CheckListRepository;
import com.likelion.besession.domain.contract.dto.response.ContractCreateResponse;
import com.likelion.besession.domain.contract.dto.response.ContractListResponse;
import com.likelion.besession.domain.contract.dto.response.ContractTypeResponse;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractStatus;
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
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final CheckListRepository checkListRepository;

    private ContractCreateResponse toResponse(Contract contract) {
        return ContractCreateResponse.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUser().getUserId())
                .contractStatus(contract.getContractStatus())
                .createdAt(contract.getCreatedAt())
                .build();
    }

    private ContractListResponse toListResponse(Contract contract) {
        return ContractListResponse.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUser().getUserId())
                .createdAt(contract.getCreatedAt())
                .build();
    }

    private ContractTypeResponse toTypeResponse(Contract contract) {
        return ContractTypeResponse.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUser().getUserId())
                .contractStatus(contract.getContractStatus())
                .createdAt(contract.getCreatedAt())
                .build();
    }

    // 계약 생성
    @Transactional
    public ContractCreateResponse createContract(Long userId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약 객체 생성
        Contract contract = Contract.builder()
                .user(user)
                .build();

        // DB에 저장
        Contract savedContract = contractRepository.save(contract);

        createCheckLists(savedContract);

        // 로그 출력
        log.info("[ContractService] 계약 생성 완료: contractId= {}, userId= {}", savedContract.getContractId(), user.getUserId());

        return toResponse(savedContract);
    }

    // 자기 자신의 계약 전체 목록 조회
    public List<ContractListResponse> allContract(Long userId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return contractRepository.findAllByUser(user)
                .stream()
                .map(this::toListResponse)
                .toList();
    }

    // 계약 단건 조회
    public ContractTypeResponse lookContract(Long userId, Long contractId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        return toTypeResponse(contract);
    }

    // 계약 삭제
    @Transactional
    public void deleteContract(Long userId, Long contractId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        // DB에서 삭제
        contractRepository.delete(contract);

        // 로그 출력
        log.info("[ContractService] 계약 삭제 성공: contractId= {}", contractId);
    }

    // 단계별 체크리스트 자동 생성
    private void createCheckLists(Contract contract) {
        List<CheckList> checkListItems = List.of(

                // 계약 전
                CheckList.builder()
                        .contract(contract)
                        .phase(ContractStatus.BEFORE)
                        .checkListTitle("등기부등본 확인")
                        .checkListContent("대법원 인터넷등기소(iros.go.kr)에서 확인하세요. 소유자, 압류, 저당권 설정 여부를 꼭 확인하세요.")
                        .build(),
                CheckList.builder()
                        .contract(contract)
                        .phase(ContractStatus.BEFORE)
                        .checkListTitle("건축물대장 확인")
                        .checkListContent("건물 주소, 면적이 실제와 일치하는지 확인하세요.")
                        .build(),
                CheckList.builder()
                        .contract(contract)
                        .phase(ContractStatus.BEFORE)
                        .checkListTitle("전세가율 확인")
                        .checkListContent("근저당 + 내 보증금 합산이 집값의 70%를 넘으면 위험합니다.")
                        .build(),

                // 계약 중
                CheckList.builder()
                        .contract(contract)
                        .phase(ContractStatus.DURING)
                        .checkListTitle("계약서 특약 사항 확인")
                        .checkListContent("특약 사항이 구두로 합의한 내용과 동일한지 확인하세요.")
                        .build(),
                CheckList.builder()
                        .contract(contract)
                        .phase(ContractStatus.DURING)
                        .checkListTitle("계약금 영수증 수령")
                        .checkListContent("계약금 지급 후 반드시 영수증을 받으세요.")
                        .build(),

                // 계약 후
                CheckList.builder()
                        .contract(contract)
                        .phase(ContractStatus.AFTER)
                        .checkListTitle("전입신고")
                        .checkListContent("잔금 지급 당일 전입신고를 완료하세요.")
                        .build(),
                CheckList.builder()
                        .contract(contract)
                        .phase(ContractStatus.AFTER)
                        .checkListTitle("확정일자 받기")
                        .checkListContent("주민센터 또는 인터넷 등기소에서 확정일자를 받으세요.")
                        .build()
        );

        // DB 저장
        checkListRepository.saveAll(checkListItems);

        // 로그 출력
        log.info("[ContractService] 체크리스트 자동 생성 완료: contractId= {}", contract.getContractId());
    }

    // 계약 상태 다음 단계로 진행
    @Transactional
    public ContractTypeResponse nextContract(Long userId, Long contractId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 계약이 존재하는지 확인
        Contract contract = contractRepository.findByContractIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        // 체크리스트 항목이 남아있는지 확인
        if (checkListRepository.existsByContractAndPhaseAndCheckListStatus(contract, contract.getContractStatus(), CheckListStatus.INCOMPLETE)) {
            log.warn("[ContractService] 체크리스트 미완료로 진행 불가: contractId= {}", contractId);
            throw new CustomException(ContractErrorCode.CONTRACT_CHECKLIST_NOT_COMPLETE);
        }

        contract.progressStatus();

        // 로그 출력
        log.info("[ContractService] 계약 상태 진행 완료: contractId= {}, status= {}", contractId, contract.getContractStatus());

        return toTypeResponse(contract);
    }
}
