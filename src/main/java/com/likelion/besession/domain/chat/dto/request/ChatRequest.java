package com.likelion.besession.domain.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "ChatRequest: 채팅 요청 DTO")
public class ChatRequest {

    @NotBlank(message = "메시지 내용은 필수입니다.")
    @Schema(description = "사용자 메시지", example = "이 계약서의 특약 사항을 설명해줘")
    private String message;
}
