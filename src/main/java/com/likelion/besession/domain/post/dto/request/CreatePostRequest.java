package com.likelion.besession.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
public class CreatePostRequest {

    @Schema(description = "게시글 제목", example = "1주차 세션: 기초 GitHub 다루기")
    private String title;

    @Schema(description = "게시글 내용", example = "GitHub를 배워요")
    private String content;
}

