package com.likjelion.besession.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
public class CreatePostRequest {

    @Schema(description = "게시물 제목", example = "1주차 세션: 기초 github 다루기")
    private String title;

    @Schema(description = "게시물 내", example = "github를 배워요")
    private String content;
}
