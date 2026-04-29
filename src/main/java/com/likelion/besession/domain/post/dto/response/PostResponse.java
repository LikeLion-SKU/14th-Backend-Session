package com.likelion.besession.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "4주차 세션")
    private String title;

    @Schema(description = "게시글 내용", example = "4주차 세션은 이 내용입니다.")
    private String content;

    @Schema(description = "게시글 조회수", example = "1")
    private Long viewCount;

    @Schema(description = "게시글 작성일자", example = "")
    private LocalDateTime createdDate;
}
