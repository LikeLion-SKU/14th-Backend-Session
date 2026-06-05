package com.likjelion.besession.week09_domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomResponse {
    private Long chatRoomId;
    private String lastMessage;
    private LocalDateTime updatedAt;
}
