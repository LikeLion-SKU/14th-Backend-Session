package com.example.week09likelion.domain.contract.dto.request;

import com.example.week09likelion.domain.contract.entity.ContractStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ContractRequest {

    @NotBlank(message = "계약 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "계약 주소는 필수입니다.")
    private String address;

    @NotNull(message = "계약 상태는 필수입니다.")
    private ContractStatus status;

    @NotNull(message = "진행률은 필수입니다.")
    @Min(value = 0, message = "진행률은 0 이상이어야 합니다.")
    @Max(value = 100, message = "진행률은 100 이하여야 합니다.")
    private Integer progress;
}