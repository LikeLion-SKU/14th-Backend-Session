package com.likjelion.besession.week09_domain.contract.service;

import com.likjelion.besession.global.exception.CustomException;
import com.likjelion.besession.global.exception.GlobalErrorCode;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.week09_domain.checklist.entity.CheckList;
import com.likjelion.besession.week09_domain.checklist.entity.ContractCheckList;
import com.likjelion.besession.week09_domain.checklist.repository.CheckListRepository;
import com.likjelion.besession.week09_domain.checklist.repository.ContractCheckListRepository;
import com.likjelion.besession.week09_domain.contract.dto.request.CreateContractRequest;
import com.likjelion.besession.week09_domain.contract.dto.request.UpdateContractStatusRequest;
import com.likjelion.besession.week09_domain.contract.dto.response.*;
import com.likjelion.besession.week09_domain.contract.entity.Contract;
import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import com.likjelion.besession.week09_domain.contract.entity.Property;
import com.likjelion.besession.week09_domain.contract.repository.ContractRepository;
import com.likjelion.besession.week09_domain.contract.repository.PropertyRepository;
import com.likjelion.besession.week09_domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final PropertyRepository propertyRepository;
    private final CheckListRepository checkListRepository;
    private final ContractCheckListRepository contractCheckListRepository;

    public CurrentContractResponse getCurrentContract(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Contract contract = contractRepository.findFirstByUserOrderByCreatedAtDesc(user)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        return CurrentContractResponse.builder()
                .contractId(contract.getId())
                .contractStatus(contract.getContractStatus())
                .processRate(contract.getProcessRate())
                .property(PropertyDto.from(contract.getProperty()))
                .build();
    }

    @Transactional
    public CreateContractResponse createContract(CustomUserDetails userDetails, CreateContractRequest request) {
        User user = userDetails.getUser();

        Property property = propertyRepository.save(Property.builder()
                .address(request.getAddress())
                .type(request.getType())
                .build());

        Contract contract = contractRepository.save(Contract.builder()
                .contractStatus(ContractStatus.BEFORE_CONTRACT)
                .user(user)
                .property(property)
                .deleted_at(LocalDateTime.of(9999, 12, 31, 0, 0))
                .build());

        createDefaultChecklists(contract);

        return CreateContractResponse.builder()
                .contractId(contract.getId())
                .propertyId(property.getId())
                .contractStatus(contract.getContractStatus())
                .processRate(contract.getProcessRate())
                .createdAt(contract.getCreatedAt())
                .build();
    }

    public ContractDetailResponse getContractDetail(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        return ContractDetailResponse.builder()
                .contractId(contract.getId())
                .contractStatus(contract.getContractStatus())
                .processRate(contract.getProcessRate())
                .property(PropertyDto.from(contract.getProperty()))
                .createdAt(contract.getCreatedAt())
                .updatedAt(contract.getUpdatedAt())
                .build();
    }

    @Transactional
    public ContractStatusUpdateResponse updateContractStatus(Long contractId, UpdateContractStatusRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        contractRepository.updateStatus(contractId, request.getContractStatus());
        return ContractStatusUpdateResponse.builder()
                .contractId(contract.getId())
                .contractStatus(request.getContractStatus())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public List<ContractListItemResponse> getContractList(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return contractRepository.findAllByUser(user).stream()
                .map(c -> ContractListItemResponse.builder()
                        .contractId(c.getId())
                        .contractStatus(c.getContractStatus())
                        .processRate(c.getProcessRate())
                        .address(c.getProperty().getAddress())
                        .type(c.getProperty().getType())
                        .createdAt(c.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private void createDefaultChecklists(Contract contract) {
        List<Object[]> defaults = List.of(
                new Object[]{"등기부등본 확인", "부동산 등기부등본을 열람하여 소유자, 저당권 등을 확인합니다.", ContractStatus.BEFORE_CONTRACT},
                new Object[]{"임대인 신원 확인", "임대인의 신분증을 확인하고 등기부등본의 소유자와 일치하는지 확인합니다.", ContractStatus.BEFORE_CONTRACT},
                new Object[]{"계약서 작성", "계약서의 모든 조항을 꼼꼼히 읽고 서명합니다.", ContractStatus.IN_CONTRACT},
                new Object[]{"보증금 입금", "계약서에 명시된 계좌로 보증금을 이체합니다.", ContractStatus.IN_CONTRACT},
                new Object[]{"이사 완료", "이삿짐을 모두 옮기고 새 주소로 주민등록을 이전합니다.", ContractStatus.AFTER_CONTRACT},
                new Object[]{"입주 점검", "전세/월세 계약 후 집 상태를 확인하고 파손 부분을 기록합니다.", ContractStatus.AFTER_CONTRACT}
        );

        for (Object[] d : defaults) {
            CheckList checkList = checkListRepository.save(CheckList.builder()
                    .title((String) d[0])
                    .content((String) d[1])
                    .contractStatus((ContractStatus) d[2])
                    .build());

            contractCheckListRepository.save(ContractCheckList.builder()
                    .contract(contract)
                    .checkList(checkList)
                    .contractStatus((ContractStatus) d[2])
                    .build());
        }
    }
}
