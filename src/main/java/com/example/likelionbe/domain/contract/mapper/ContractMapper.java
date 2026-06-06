package com.example.likelionbe.domain.contract.mapper;

import com.example.likelionbe.domain.contract.dto.ContractApplyResDto;
import com.example.likelionbe.domain.contract.dto.ContractDocumentResDto;
import com.example.likelionbe.domain.contract.dto.ContractStatusResDto;
import com.example.likelionbe.domain.contract.dto.SellerContractSummaryResDto;
import com.example.likelionbe.domain.contract.entity.Contract;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Contract 엔티티와 DTO 간 변환을 담당하는 매퍼 클래스입니다.
 */
@Component
public class ContractMapper {

    /**
     * Contract 엔티티를 ContractApplyResDto로 변환합니다.
     *
     * @param contract           변환할 Contract 엔티티
     * @param checklistsCreated 생성된 체크리스트 항목 수
     * @return 변환된 ContractApplyResDto
     */
    public ContractApplyResDto toContractApplyResDto(Contract contract, int checklistsCreated) {
        return ContractApplyResDto.builder()
                .contractId(contract.getId())
                .listingId(contract.getListing().getId())
                .buyerId(contract.getBuyer().getId())
                .contractStatus(contract.getContractStatus())
                .checklistsCreated(checklistsCreated)
                .createdAt(contract.getCreatedAt())
                .build();
    }

    /**
     * Contract 엔티티를 ContractStatusResDto로 변환합니다.
     *
     * @param contract 변환할 Contract 엔티티
     * @return 변환된 ContractStatusResDto
     */
    public ContractStatusResDto toContractStatusResDto(Contract contract) {
        LocalDateTime updatedAt = switch (contract.getContractStatus()) {
            case APPROVED -> contract.getApprovedAt();
            case REJECTED -> contract.getRejectedAt();
            case COMPLETED -> contract.getCompletedAt();
            default -> contract.getCreatedAt();
        };

        return ContractStatusResDto.builder()
                .contractId(contract.getId())
                .contractStatus(contract.getContractStatus())
                .updatedAt(updatedAt)
                .build();
    }

    /**
     * Contract 엔티티를 SellerContractSummaryResDto로 변환합니다.
     *
     * @param contract 변환할 Contract 엔티티
     * @return 변환된 SellerContractSummaryResDto
     */
    public SellerContractSummaryResDto toSellerContractSummaryResDto(Contract contract) {
        return SellerContractSummaryResDto.builder()
                .contractId(contract.getId())
                .buyerId(contract.getBuyer().getId())
                .buyerName(contract.getBuyer().getName())
                .contractStatus(contract.getContractStatus())
                .requestMessage(contract.getRequestMessage())
                .createdAt(contract.getCreatedAt())
                .build();
    }

    /**
     * Contract 엔티티를 ContractDocumentResDto로 변환합니다.
     *
     * @param contract 변환할 Contract 엔티티
     * @return 변환된 ContractDocumentResDto
     */
    public ContractDocumentResDto toContractDocumentResDto(Contract contract) {
        return ContractDocumentResDto.builder()
                .contractId(contract.getId())
                .contractDocumentContent(contract.getContractDocumentContent())
                .aiSummaryContent(contract.getAiSummaryContent())
                .cautionContent(contract.getCautionContent())
                .contractStatus(contract.getContractStatus())
                .build();
    }
}
