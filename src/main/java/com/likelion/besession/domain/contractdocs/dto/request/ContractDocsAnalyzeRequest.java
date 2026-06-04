package com.likelion.besession.domain.contractdocs.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "ContractDocsAnalyzeRequest: 계약서 생성 요청 DTO")
public class ContractDocsAnalyzeRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Schema(description = "계약서 제목", example = "서경로 300길 2층 계약서")
    private String title;

    @NotBlank(message = "계약서 이미지는 필수입니다.")
    @Schema(description = "계약서 촬영 이미지 URL", example = "https://example.com/images/contract.jpg")
    private String contractImage;
}
