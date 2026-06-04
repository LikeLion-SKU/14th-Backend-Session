package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.Contract;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ContractPartiesResponse: 계약 당사자 응답 DTO")
public class ContractPartiesResponse {

    @Schema(description = "계약서 ID", example = "1")
    private Long contractId;

    @Schema(description = "계약자(나) 이름", example = "홍길동")
    private String ownerName;

    @Schema(description = "계약자(나) 이메일", example = "test@example.com")
    private String ownerEmail;

    @Schema(description = "계약 상대방 이름", example = "김임대")
    private String partnerName;

    public static ContractPartiesResponse from(Contract contract) {
        return ContractPartiesResponse.builder()
                .contractId(contract.getId())
                .ownerName(contract.getUser().getName())
                .ownerEmail(contract.getUser().getEmail())
                .partnerName(contract.getContractPartnerName())
                .build();
    }
}
