package com.example.besession.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${spring.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // 허용할 Origin
        config.setAllowedOrigins(Arrays.asList(allowedOrigins));

        // 허용할 HTTP Method
        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        // 허용할 Header
        config.setAllowedHeaders(List.of("*"));

        // 프론트에서 Authorization 헤더 읽기 허용
        config.setExposedHeaders(List.of("Authorization"));

        // 쿠키 / 인증 정보 허용
        config.setAllowCredentials(true);

        // 모든 경로에 적용
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}