package com.likjelion.besession.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse {

    //response 같은 경우엔 id를 포함 시키는 경우가 많음
    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "4주차 세션: Entity, Controller, Swagger")
    private String title;

    @Schema(description = "게시글 내용", example = "Entity, Controller, Swagger를 익혀요")
    private String content;
}