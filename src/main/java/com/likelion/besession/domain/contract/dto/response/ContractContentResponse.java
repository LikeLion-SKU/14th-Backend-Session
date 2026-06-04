package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.Contract;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@Schema(title = "ContractContentResponse: 계약서 내용 응답 DTO")
public class ContractContentResponse {

    @Schema(description = "계약서 ID", example = "1")
    private Long contractId;

    @Schema(description = "계약서 파일 URL")
    private String contractDoc;

    @Schema(description = "계약 시작일")
    private LocalDate startDate;

    @Schema(description = "계약 종료일")
    private LocalDate endDate;

    public static ContractContentResponse from(Contract contract) {
        return ContractContentResponse.builder()
                .contractId(contract.getId())
                .contractDoc(contract.getContractDoc())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .build();
    }
}
