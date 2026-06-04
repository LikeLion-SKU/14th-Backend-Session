package com.likelion.besession.domain.contract.dto.request;

import com.likelion.besession.domain.contract.entity.Stage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateContractRequest {
    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @NotNull(message = "진행 단계는 필수입니다.")
    private Stage stage;
}