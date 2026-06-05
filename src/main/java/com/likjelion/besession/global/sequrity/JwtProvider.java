package com.likjelion.besession.global.sequrity;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE = "ACCESS_TOKEN";

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {

        // JWT 서명에 사용할 SecretKey 생성
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // Access Token 만료 시간
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String createAccessToken(CustomUserDetailsV1 userDetails) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + accessTokenExpiration);

        // 사용자 권한 목록 추출
        List<String> roles =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

        // Access Token 생성
        return Jwts.builder()
                .subject(String.valueOf(userDetails.getUserV1().getId())) // 토큰 주인 식별값
                .claim("type", TOKEN_TYPE) // 토큰 타입
                .claim("email", userDetails.getUsername()) // 사용자 이메일
                .claim("roles", roles) // 사용자 권한
                .issuedAt(now) // 발급 시간
                .expiration(expiredAt) // 만료 시간
                .signWith(secretKey) // 서명
                .compact();
    }

    public String createRefreshToken(CustomUserDetailsV1 userDetails) {
        Date now = new Date();
        // 💡 엑세스 토큰보다 훨씬 긴 만료 시간을 부여합니다. (보통 2주)
        Date expiredAt = new Date(now.getTime() + refreshTokenExpiration);

        // Refresh Token 생성
        return Jwts.builder()
                .subject(String.valueOf(userDetails.getUserV1().getId())) // 토큰 주인 식별값 (ID)
                .claim("type", "REFRESH_TOKEN") // 💡 엑세스 토큰과 확실히 구분되도록 타입 지정
                .issuedAt(now)                  // 발급 시간
                .expiration(expiredAt)          // 만료 시간
                .signWith(secretKey)            // 서명
                .compact();
    }

    public String createAccessToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .subject(String.valueOf(userDetails.getUser().getId()))
                .claim("type", TOKEN_TYPE)
                .claim("email", userDetails.getUsername())
                .claim("roles", List.of())
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .subject(String.valueOf(userDetails.getUser().getId()))
                .claim("type", "REFRESH_TOKEN")
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // 토큰 서명, 만료 시간, 타입 검증
            Claims claims = extractClaims(token);
            String type = claims.get("type", String.class);

            return TOKEN_TYPE.equals(type);
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        // 토큰에서 userId 추출
        return Long.valueOf(extractClaims(token).getSubject());
    }

    private Claims extractClaims(String token) {
        // 토큰을 파싱하고 Payload 내용 추출
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}