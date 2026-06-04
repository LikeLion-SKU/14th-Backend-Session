package com.likelion.besession.domain.chat.entity;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 계약서에 대한 채팅인지 (채팅방 구분 기준)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    // 메시지를 주고받은 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 발화자 역할: USER(사용자 입력) / SYSTEM(AI 응답)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatRole role;

    // 메시지 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public ChatMessage(Contract contract, User user, ChatRole role, String content) {
        this.contract = contract;
        this.user = user;
        this.role = role;
        this.content = content;
    }
}
