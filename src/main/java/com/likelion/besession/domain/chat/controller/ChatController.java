package com.likelion.besession.domain.chat.controller;

import com.likelion.besession.domain.chat.dto.request.ChatRequest;
import com.likelion.besession.domain.chat.dto.response.ChatMessageResponse;
import com.likelion.besession.domain.chat.dto.response.ChatRoomResponse;
import com.likelion.besession.domain.chat.service.ChatService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.config.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "채팅 관련 API")
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "채팅 목록 조회", description = "계약서별 채팅방 목록을 조회하는 API")
    @GetMapping("/history")
    public ResponseEntity<BaseResponse<List<ChatRoomResponse>>> getChatRooms(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ChatRoomResponse> response = chatService.getChatRooms(userDetails.getUser().getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "채팅 내역 조회", description = "특정 계약서의 채팅 메시지 내역을 조회하는 API")
    @GetMapping("/{contractId}")
    public ResponseEntity<BaseResponse<List<ChatMessageResponse>>> getChatHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        List<ChatMessageResponse> response = chatService.getChatHistory(userDetails.getUser().getId(), contractId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "채팅 하기", description = "계약서에 대해 AI에게 메시지를 보내는 API")
    @PostMapping("/{contractId}")
    public ResponseEntity<BaseResponse<List<ChatMessageResponse>>> sendMessage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId,
            @Valid @RequestBody ChatRequest request) {
        List<ChatMessageResponse> response = chatService.sendMessage(
                userDetails.getUser().getId(), contractId, request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "채팅 히스토리 삭제", description = "특정 계약서의 채팅 내역을 전체 삭제하는 API")
    @DeleteMapping("/{contractId}/history")
    public ResponseEntity<BaseResponse<Void>> deleteChatHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        chatService.deleteChatHistory(userDetails.getUser().getId(), contractId);
        return ResponseEntity.ok(BaseResponse.success("채팅 내역이 삭제되었습니다.", null));
    }
}
