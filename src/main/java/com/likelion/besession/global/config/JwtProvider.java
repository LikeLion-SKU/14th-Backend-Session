package com.likelion.besession.global.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtProvider {
    private static final String TOKEN_TYPE = "ACCESS_TOKEN";
    private final SecretKey secretKey;
    private final long accessTokenExpiration;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.access-token-expiration}") long accessTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public String createAccessToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + accessTokenExpiration);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(String.valueOf(userDetails.getUser().getId()))
                .claim("type", TOKEN_TYPE)
                .claim("email", userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }
    // ... validateToken, getUserId, extractClaims 메서드 포함 (36페이지)
}