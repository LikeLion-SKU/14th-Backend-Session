package com.likelion.besession.domain.chat.service;

import com.likelion.besession.domain.chat.dto.request.ChatRequest;
import com.likelion.besession.domain.chat.dto.response.ChatMessageResponse;
import com.likelion.besession.domain.chat.dto.response.ChatRoomResponse;
import com.likelion.besession.domain.chat.entity.ChatMessage;
import com.likelion.besession.domain.chat.entity.ChatRole;
import com.likelion.besession.domain.chat.repository.ChatMessageRepository;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    // 채팅 목록 조회: 사용자의 모든 메시지에서 계약서별 최신 메시지 1개씩 추출
    @Transactional(readOnly = true)
    public List<ChatRoomResponse> getChatRooms(Long userId) {
        User user = getUser(userId);
        List<ChatMessage> messages = chatMessageRepository.findAllByUserOrderByCreatedDateDesc(user);

        // 계약서 ID를 키로, 가장 먼저 등장한(최신) 메시지를 값으로 수집
        Map<Long, ChatMessage> latestByContract = messages.stream()
                .collect(Collectors.toMap(
                        m -> m.getContract().getId(),
                        m -> m,
                        (existing, replacement) -> existing  // 중복 시 기존(더 최신) 값 유지
                ));

        return latestByContract.values().stream()
                .map(m -> ChatRoomResponse.builder()
                        .contractId(m.getContract().getId())
                        .contractPartnerName(m.getContract().getContractPartnerName())
                        .lastMessage(m.getContent())
                        .lastMessageAt(m.getCreatedDate())
                        .build())
                .toList();
    }

    // 채팅 내역 조회: 특정 계약서에 대한 메시지를 시간 오름차순으로 반환
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getChatHistory(Long userId, Long contractId) {
        User user = getUser(userId);
        Contract contract = getContractByIdAndUser(contractId, user);
        return chatMessageRepository.findAllByContractAndUserOrderByCreatedDateAsc(contract, user)
                .stream()
                .map(ChatMessageResponse::from)
                .toList();
    }

    // 메시지 전송: 사용자 메시지 저장 후 AI 응답(stub) 생성해서 함께 반환
    public List<ChatMessageResponse> sendMessage(Long userId, Long contractId, ChatRequest request) {
        User user = getUser(userId);
        Contract contract = getContractByIdAndUser(contractId, user);

        // 1. 사용자 메시지 저장 (role = USER)
        ChatMessage userMessage = ChatMessage.builder()
                .contract(contract)
                .user(user)
                .role(ChatRole.USER)
                .content(request.getMessage())
                .build();
        chatMessageRepository.save(userMessage);

        // 2. AI 응답 생성 및 저장 (role = SYSTEM)
        // 실제 AI 서비스 연동 시 generateAiReply()를 AI API 호출로 교체
        String aiReply = generateAiReply(request.getMessage(), contract);
        ChatMessage systemMessage = ChatMessage.builder()
                .contract(contract)
                .user(user)
                .role(ChatRole.SYSTEM)
                .content(aiReply)
                .build();
        chatMessageRepository.save(systemMessage);

        // 3. 사용자 메시지 + AI 응답을 함께 반환
        List<ChatMessageResponse> result = new ArrayList<>();
        result.add(ChatMessageResponse.from(userMessage));
        result.add(ChatMessageResponse.from(systemMessage));
        return result;
    }

    // 채팅 히스토리 삭제: 해당 계약서에 대한 본인 메시지 전체 삭제
    public void deleteChatHistory(Long userId, Long contractId) {
        User user = getUser(userId);
        Contract contract = getContractByIdAndUser(contractId, user);
        chatMessageRepository.deleteAllByContractAndUser(contract, user);
    }

    // AI 응답 stub - 실제 AI 연동 시 이 메서드를 교체
    private String generateAiReply(String message, Contract contract) {
        return String.format("[%s 계약서 AI] '%s' 에 대한 답변입니다. 계약 내용을 분석하여 상세한 답변을 제공합니다.",
                contract.getContractPartnerName(), message);
    }

    // 계약서 조회 공통 메서드: 본인 소유 여부 검증 포함
    private Contract getContractByIdAndUser(Long contractId, User user) {
        return contractRepository.findByIdAndUser(contractId, user)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
