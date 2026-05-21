package com.likjelion.besession.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
public class CreatePostRequest {

    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    @Schema(description = "게시물 제목", example = "1주차 세션: 기초 github 다루기")
    private String title;

    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    @Schema(description = "게시물 내용", example = "github를 배워요")
    private String content;
}
