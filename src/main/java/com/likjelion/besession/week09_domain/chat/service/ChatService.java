package com.likjelion.besession.week09_domain.chat.service;

import com.likjelion.besession.week09_domain.chat.dto.request.SendMessageRequest;
import com.likjelion.besession.week09_domain.chat.dto.response.ChatMessageResponse;
import com.likjelion.besession.week09_domain.chat.dto.response.ChatRoomResponse;
import com.likjelion.besession.week09_domain.chat.dto.response.SendMessageResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private static final String AI_FIXED_RESPONSE = "계약서에서 보증금 반환, 특약, 계약 해지 조건을 우선 확인하는 것이 좋습니다.";
    private static final String AI_WELCOME_MESSAGE = "안녕하세요. 계약서와 부동산 계약 과정에서 궁금한 점을 물어보세요.";

    public ChatRoomResponse getMyChatRoom() {
        return ChatRoomResponse.builder()
                .chatRoomId(1L)
                .lastMessage(AI_FIXED_RESPONSE)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public List<ChatMessageResponse> getMessages(Long chatRoomId) {
        return List.of(
                ChatMessageResponse.builder()
                        .chatMessageId(1L)
                        .sender("AI")
                        .content(AI_WELCOME_MESSAGE)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    public SendMessageResponse sendMessage(Long chatRoomId, SendMessageRequest request) {
        ChatMessageResponse userMessage = ChatMessageResponse.builder()
                .chatMessageId(1L)
                .sender("USER")
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        ChatMessageResponse aiMessage = ChatMessageResponse.builder()
                .chatMessageId(2L)
                .sender("AI")
                .content(AI_FIXED_RESPONSE)
                .createdAt(LocalDateTime.now())
                .build();

        return SendMessageResponse.builder()
                .userMessage(userMessage)
                .aiMessage(aiMessage)
                .build();
    }
}
