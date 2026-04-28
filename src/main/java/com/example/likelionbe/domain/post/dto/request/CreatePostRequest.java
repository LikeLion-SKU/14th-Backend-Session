package com.example.likelionbe.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
public record CreatePostRequest(
    @Schema(description = "게시글 제목", example = "제목1")
    String title,

    @Schema(description = "게시글 내용", example = "본문...")
    String content
) {
}
