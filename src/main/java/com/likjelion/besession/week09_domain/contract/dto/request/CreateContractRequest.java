package com.likjelion.besession.week09_domain.contract.dto.request;

import com.likjelion.besession.week09_domain.contract.entity.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateContractRequest {
    private String address;
    private Type type;
}
