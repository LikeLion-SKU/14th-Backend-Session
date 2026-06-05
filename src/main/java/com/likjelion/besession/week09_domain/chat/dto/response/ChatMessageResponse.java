package com.likjelion.besession.week09_domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponse {
    private Long chatMessageId;
    private String sender;
    private String content;
    private LocalDateTime createdAt;
}
