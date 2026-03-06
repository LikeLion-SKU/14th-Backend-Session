package com.likelion.besession.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public class UpdatePostRequest {

    @Schema(description = "게시글 제목", example = "5주차 세션: DTO, Service, Repository")
    private String title;

    @Schema(description = "게시글 내용", example = "DB 접근 및 비즈니스 로직 구현을 익혀요")
    private String content;
}


