package com.likelion.besession.domain.chat.dto.response;

import com.likelion.besession.domain.chat.entity.ChatMessage;
import com.likelion.besession.domain.chat.entity.ChatRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "ChatMessageResponse: 채팅 메시지 응답 DTO")
public class ChatMessageResponse {

    @Schema(description = "메시지 ID", example = "1")
    private Long id;

    @Schema(description = "발화자 역할", example = "USER")
    private ChatRole role;

    @Schema(description = "메시지 내용", example = "이 계약서의 특약 사항을 설명해줘")
    private String content;

    @Schema(description = "전송 시각")
    private LocalDateTime createdAt;

    public static ChatMessageResponse from(ChatMessage message) {
        return ChatMessageResponse.builder()
                .id(message.getId())
                .role(message.getRole())
                .content(message.getContent())
                .createdAt(message.getCreatedDate())
                .build();
    }
}
