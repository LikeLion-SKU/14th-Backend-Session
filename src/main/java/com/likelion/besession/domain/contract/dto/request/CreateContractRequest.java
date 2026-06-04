package com.likelion.besession.domain.contract.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Schema(title = "CreateContractRequest: 계약서 생성 요청 DTO")
public class CreateContractRequest {

    @NotBlank(message = "계약 상대방 이름은 필수입니다.")
    @Schema(description = "계약 상대방 이름", example = "김민호")
    private String contractPartnerName;

    @Schema(description = "계약서 파일 URL 또는 경로", example = "https://example.com/contract.pdf")
    private String contractDoc;

    @Schema(description = "계약 시작일", example = "2026-06-01")
    private LocalDate startDate;

    @Schema(description = "계약 종료일", example = "2026-06-03")
    private LocalDate endDate;
}
