package com.example.likelionbe.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public record UpdatePostRequest(
        @Schema(description = "게시글 PK", example = "1")
        Long postId,

        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        @Schema(description = "게시글 제목", example = "제목1")
        String title,

        @NotBlank(message = "내용은 비어있을 수 없습니다.")
        @Schema(description = "게시글 내용", example = "본문...")
        String content
) {
}
