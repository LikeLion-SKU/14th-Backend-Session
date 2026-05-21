package com.example.besession.global.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Authorization 헤더에서 토큰 추출
        String token = resolveToken(request);

        try {

            // 토큰이 존재하고 유효한 경우
            if (token != null && jwtProvider.validateToken(token)) {

                // 토큰에서 userId 추출
                Long userId = jwtProvider.getUserId(token);

                // 사용자 조회
                CustomUserDetails userDetails =
                        customUserDetailsService.loadUserById(userId);

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // SecurityContext에 저장
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }

        } catch (Exception e) {

            // 인증 실패 시 인증 정보 제거
            SecurityContextHolder.clearContext();
        }

        // 다음 필터 실행
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 Bearer 토큰 추출
    private String resolveToken(HttpServletRequest request) {

        String authorization =
                request.getHeader("Authorization");

        // Bearer 형식이 아니면 null
        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {

            return null;
        }

        // "Bearer " 제거
        return authorization.substring(7);
    }
}