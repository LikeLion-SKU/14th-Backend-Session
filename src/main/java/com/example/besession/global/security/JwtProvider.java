package com.example.besession.global.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE = "ACCESS_TOKEN";

    private final SecretKey secretKey;
    private final long accessTokenExpiration;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration
    ) {

        // JWT 서명용 SecretKey 생성
        this.secretKey =
                Keys.hmacShaKeyFor(
                        secret.getBytes(StandardCharsets.UTF_8)
                );

        // AccessToken 만료 시간
        this.accessTokenExpiration = accessTokenExpiration;
    }

    // AccessToken 생성
    public String createAccessToken(CustomUserDetails userDetails) {

        Date now = new Date();

        Date expiredAt =
                new Date(now.getTime() + accessTokenExpiration);

        // 사용자 권한 추출
        List<String> roles =
                userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

        return Jwts.builder()

                // 토큰 주인 식별값
                .subject(
                        String.valueOf(
                                userDetails.getUser().getId()
                        )
                )

                // 토큰 타입
                .claim("type", TOKEN_TYPE)

                // 이메일
                .claim("email", userDetails.getUsername())

                // 권한
                .claim("roles", roles)

                // 발급 시간
                .issuedAt(now)

                // 만료 시간
                .expiration(expiredAt)

                // 서명
                .signWith(secretKey)

                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {

        try {

            Claims claims = extractClaims(token);

            String type =
                    claims.get("type", String.class);

            return TOKEN_TYPE.equals(type);

        } catch (Exception e) {

            return false;
        }
    }

    // userId 추출
    public Long getUserId(String token) {

        return Long.valueOf(
                extractClaims(token).getSubject()
        );
    }

    // Claims 추출
    private Claims extractClaims(String token) {

        return Jwts.parser()

                .verifyWith(secretKey)

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
}