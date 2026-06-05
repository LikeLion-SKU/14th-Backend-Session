package com.likjelion.besession.week09_domain.chat.entity;

import com.likjelion.besession.week09_domain.user.entity.User;
import com.likjelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "chat_room")
public class ChatRoom extends BaseTimeEntity {

    @Id @Column(name = "chat_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



}
