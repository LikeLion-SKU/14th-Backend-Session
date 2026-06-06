package com.likelion.besession.domain.checklist.dto;

import com.likelion.besession.domain.checklist.entity.Checklist;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChecklistResponse {

    private Long id;
    private String title;
    private String contractStep;
    private Boolean checked;

    public static ChecklistResponse from(Checklist checklist) {
        return ChecklistResponse.builder()
                .id(checklist.getId())
                .title(checklist.getTitle())
                .contractStep(checklist.getContractStep())
                .checked(checklist.getChecked())
                .build();
    }
}