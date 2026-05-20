package com.likelion.besession.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public class UpdatePostRequest {

    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    @Schema(description = "게시글 제목", example = "5주차 세션: DTO, Service, Repository")
    private String title;

    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    @Schema(description = "게시글 내용", example = "DB 접근 및 비즈니스 로직 구현을 익혀요")
    private String content;
}