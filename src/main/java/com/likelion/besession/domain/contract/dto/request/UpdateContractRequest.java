package com.likelion.besession.domain.contract.dto.request;

import com.likelion.besession.domain.contract.entity.Stage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateContractRequest {
    @NotNull(message = "진행 단계는 필수입니다.")
    private Stage stage;

    @NotNull(message = "진행률은 필수입니다.")
    private Integer progressRate;
}