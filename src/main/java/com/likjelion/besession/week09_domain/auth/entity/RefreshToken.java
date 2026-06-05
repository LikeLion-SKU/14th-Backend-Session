package com.likjelion.besession.week09_domain.auth.entity;

import com.likjelion.besession.global.common.BaseTimeEntity;
import com.likjelion.besession.week09_domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_token")
public class RefreshToken extends BaseTimeEntity {

    @Id @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "expire_at")
    private LocalDateTime expireAt;


    //토큰 갱신 매서드( 로그인 시점에 기존 토큰이 남아있다면 갱신 )
    public void updateToken(String newRefreshToken, LocalDateTime newExpireAt) {
        this.token = newRefreshToken;
        this.expireAt = newExpireAt;
    }

}
