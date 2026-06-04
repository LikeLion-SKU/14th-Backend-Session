package com.likelion.besession.domain.contract.service;

import com.likelion.besession.domain.contract.dto.request.CreateContractRequest;
import com.likelion.besession.domain.contract.dto.request.UpdateContractRequest;
import com.likelion.besession.domain.contract.dto.response.ContractResponse;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.contract.entity.Stage;
import com.likelion.besession.domain.contract.entity.CheckList;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.contract.repository.CheckListRepository;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final CheckListRepository checkListRepository;

    @Transactional
    public ContractResponse createContract(CreateContractRequest request){
        log.info("[ContractService] 계약 생성 요청 - address: {}", request.getAddress());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ContractErrorCode.USER_NOT_FOUND));

        if (contractRepository.existsByUser(user)) {
          throw new CustomException(ContractErrorCode.CONTRACT_ALREADY_EXISTS);
        }

        Contract contract = Contract.builder()
                .user(user)
                .address(request.getAddress())
                .stage(request.getStage())
                .progressRate(0)
                .build();

        Contract savedContract = contractRepository.save(contract);

        log.info("[ContractService] 계약 생성 완료 - contractId: {}", savedContract.getId());

        createDefaultCheckLists(savedContract);

        return toContractResponse(savedContract);
    }

    @Transactional(readOnly = true)
    public List<ContractResponse> getAllContracts(){
        List<Contract> contractList = contractRepository.findAll();
        log.debug("[ContractService] 계약 전체 조회 완료 - 총 {}건", contractList.size());
        return contractList.stream().map(this::toContractResponse).toList();
    }

    @Transactional(readOnly = true)
    public ContractResponse getContractById(Long contractId){
        log.debug("[ContractService] 계약 단건 조회 - contractId: {}", contractId);
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        return toContractResponse(contract);
    }

    @Transactional
    public ContractResponse updateContract(Long contractId, UpdateContractRequest request){
        log.info("[ContractService] 계약 수정 요청 - contractId: {}", contractId);

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        contract.updateContract(request); // 엔티티에 updateContract 메서드 구현 필요

        contractRepository.save(contract);

        return toContractResponse(contract);
    }

    @Transactional
    public Boolean deleteContract(Long contractId){
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        contractRepository.delete(contract);
        return true;
    }

    private ContractResponse toContractResponse(Contract contract){
        return ContractResponse.builder()
                .contractId(contract.getId())
                .userId(contract.getUser().getId())
                .address(contract.getAddress())
                .stage(contract.getStage())
                .progressRate(contract.getProgressRate())
                .build();
    }

    private void createDefaultCheckLists(Contract contract) {
        log.info("[ContractService] 기본 체크리스트 생성 시작 - contractId: {}", contract.getId());

        CheckList checkList1 = CheckList.builder()
                .contract(contract).stage(Stage.BEFORE).title("등기부등본 확인")
                .verificationMethod("대법원 인터넷등기소(iros.go.kr)")
                .warningNote("-표제부: 건물주소, 면적이 실제와 일치하는지\n-갑구: 소유자가 계약하는 집주인과 동일인인지\n-을구: 근저당권 설정 금액 확인")
                .isChecked(false).build();

        CheckList checkList2 = CheckList.builder()
                .contract(contract).stage(Stage.BEFORE).title("건축물대장 확인")
                .isChecked(false).build();

        CheckList checkList3 = CheckList.builder()
                .contract(contract).stage(Stage.DURING).title("특약 사항 확인")
                .isChecked(false).build();

        CheckList checkList4 = CheckList.builder()
                .contract(contract).stage(Stage.AFTER).title("전입신고 및 확정일자 받기")
                .isChecked(false).build();

        checkListRepository.saveAll(List.of(checkList1, checkList2, checkList3, checkList4));
        log.info("[ContractService] 기본 체크리스트 생성 완료");
    }
}