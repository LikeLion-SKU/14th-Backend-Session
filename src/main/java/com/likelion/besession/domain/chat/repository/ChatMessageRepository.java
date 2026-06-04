package com.likelion.besession.domain.chat.repository;

import com.likelion.besession.domain.chat.entity.ChatMessage;
import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 특정 계약서의 채팅 내역을 시간 오름차순으로 조회 (대화 흐름 순서)
    List<ChatMessage> findAllByContractAndUserOrderByCreatedDateAsc(Contract contract, User user);

    // 사용자의 전체 채팅 메시지를 최신순으로 조회 (채팅방 목록 구성용)
    List<ChatMessage> findAllByUserOrderByCreatedDateDesc(User user);

    // 특정 계약서의 채팅 내역 전체 삭제
    void deleteAllByContractAndUser(Contract contract, User user);
}
