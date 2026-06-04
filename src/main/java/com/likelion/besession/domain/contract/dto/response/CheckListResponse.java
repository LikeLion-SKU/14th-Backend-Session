package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.Stage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckListResponse {
    private Long checkListId;
    private Stage stage;
    private String title;
    private String verificationMethod;
    private String verificationUrl;
    private String warningNote;
    private boolean isChecked;
}