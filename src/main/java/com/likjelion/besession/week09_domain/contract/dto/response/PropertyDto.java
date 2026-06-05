package com.likjelion.besession.week09_domain.contract.dto.response;

import com.likjelion.besession.week09_domain.contract.entity.Property;
import com.likjelion.besession.week09_domain.contract.entity.Type;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyDto {
    private Long propertyId;
    private String address;
    private Type type;

    public static PropertyDto from(Property property) {
        return PropertyDto.builder()
                .propertyId(property.getId())
                .address(property.getAddress())
                .type(property.getType())
                .build();
    }
}
