package com.likelion.besession.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl(contextPath);
        localServer.setDescription("Local Server");

        // API 명세를 정의하는 객체
        return new OpenAPI()
                // 1. API가 동작하는 서버 정보 설정 (여러 Server 객체들을 생성해서, 여러 서버를 추가할 수도 있다)
                .addServersItem(localServer)
                // 2. 전역으로 모든 API에 bearerAuth 보안 요구사항 적용
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                // 3. bearerAuth가 어떤 방식인지 정의
                .components(
                        new Components().addSecuritySchemes(
                                "bearerAuth",               // 2에서 참조하는 이름과 일치해야 함
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)   // HTTP 인증 방식
                                        .scheme("bearer")                  // Bearer 토큰 방식
                                        .bearerFormat("JWT")               // 토큰 형식 (문서용, 실제 검증 X)
                        )
                )
                .info(new Info().title("LikeLion API 명세서").version("1.0").description("Swagger 실습"));
    }

    @Bean
    public GroupedOpenApi customGroupedOpenAPI() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build();
    }

}
