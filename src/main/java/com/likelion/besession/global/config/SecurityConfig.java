package com.likelion.besession.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration // 설정 클래스
@EnableWebSecurity // Spring Security 활성화
@EnableMethodSecurity // 메서드 단위 권한 활성화
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfigurationSource corsConfigurationSource;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // CorsConfig에서 작성한 CORS 설정을 Spring Security에 연결
        .cors(cors -> cors.configurationSource(corsConfigurationSource))

        // JWT 기반 API 서버에서는 CSRF 비활성화
        .csrf(AbstractHttpConfigurer::disable)

        // 서버에 로그인 상태를 저장하지 않는 Stateless 방식
        .sessionManagement(
            session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // API별 접근 권한 설정
        .authorizeHttpRequests(
            auth ->
                auth.
                    // 기본 에러 경로 누구나 접근 가능
                    requestMatchers("/error")
                    .permitAll()

                    // Swagger 문서 접근 허용
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll()

                    // 회원가입, 로그인 등의 API는 로그인 전에도 접근 가능
                    .requestMatchers("/api/auth/**", "/api/users/signup")
                    .permitAll()

                    // posts 밑의 GET 요청만 누구나 접근 가능
                    .requestMatchers(HttpMethod.GET, "/api/posts/**")
                    .permitAll()

                    // 그 외 모든 API는 로그인한 사용자만 접근 가능
                    .anyRequest()
                    .authenticated());

    return http.build();
  }

  // 비밀번호를 암호화하고 검증할 때 사용
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}