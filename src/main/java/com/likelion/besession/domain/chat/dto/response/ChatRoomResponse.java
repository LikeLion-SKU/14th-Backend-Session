package com.likelion.besession.domain.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "ChatRoomResponse: 채팅 목록 응답 DTO")
public class ChatRoomResponse {

    @Schema(description = "계약서 ID", example = "1")
    private Long contractId;

    @Schema(description = "계약 상대방 이름", example = "김임대")
    private String contractPartnerName;

    @Schema(description = "마지막 메시지 내용")
    private String lastMessage;

    @Schema(description = "마지막 메시지 시각")
    private LocalDateTime lastMessageAt;
}
