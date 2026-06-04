package com.likelion.besession.domain.contract.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCheckListRequest {
    @NotNull(message = "체크 여부는 필수입니다.")
    private Boolean isChecked;
}