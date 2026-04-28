package com.example.likelionbe.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title = "PostResponse: 게시물 응답 DTO")
public record PostResponse(
        @Schema(description = "게시글 ID", example = "1")
        Long postId,

        @Schema(description = "게시글 제목", example = "4주차 세션: Entity, Controller, Swagger")
        String title,

        @Schema(description = "게시글 내용", example = "Entity, Controller, Swagger를 익혀요")
        String content
) {
}
