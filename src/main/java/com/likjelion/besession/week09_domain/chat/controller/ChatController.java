package com.likjelion.besession.week09_domain.chat.controller;

import com.likjelion.besession.global.common.BaseResponse;
import com.likjelion.besession.week09_domain.chat.dto.request.SendMessageRequest;
import com.likjelion.besession.week09_domain.chat.dto.response.ChatMessageResponse;
import com.likjelion.besession.week09_domain.chat.dto.response.ChatRoomResponse;
import com.likjelion.besession.week09_domain.chat.dto.response.SendMessageResponse;
import com.likjelion.besession.week09_domain.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chat", description = "AI 채팅 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "내 채팅방 조회", description = "현재 사용자의 기본 AI 채팅방 정보를 조회합니다. MVP에서는 실제 채팅방 생성 로직 없이 고정된 채팅방 정보를 반환합니다.")
    @GetMapping("/rooms/me")
    public ResponseEntity<BaseResponse<ChatRoomResponse>> getMyChatRoom() {
        return ResponseEntity.ok(BaseResponse.success(200, "채팅방 조회 성공", chatService.getMyChatRoom()));
    }

    @Operation(summary = "채팅 메시지 조회", description = "특정 채팅방의 메시지 목록을 조회합니다. MVP에서는 저장된 메시지 대신 고정된 예시 메시지를 반환합니다.")
    @GetMapping("/rooms/{chatRoomId}/messages")
    public ResponseEntity<BaseResponse<List<ChatMessageResponse>>> getMessages(
            @PathVariable Long chatRoomId) {
        return ResponseEntity.ok(BaseResponse.success(200, "메시지 조회 성공", chatService.getMessages(chatRoomId)));
    }

    @Operation(summary = "메시지 전송 및 수신", description = "사용자의 메시지를 입력받고 AI 답변을 반환합니다. MVP에서는 실제 AI 연동 없이 정해진 답변 문자열을 반환합니다.")
    @PostMapping("/rooms/{chatRoomId}/messages")
    public ResponseEntity<BaseResponse<SendMessageResponse>> sendMessage(
            @PathVariable Long chatRoomId,
            @RequestBody SendMessageRequest request) {
        return ResponseEntity.ok(BaseResponse.success(200, "메시지 전송 성공", chatService.sendMessage(chatRoomId, request)));
    }
}
