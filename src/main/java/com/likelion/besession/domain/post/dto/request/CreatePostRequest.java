package com.likelion.besession.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

// record 방식을 사용할 경우, 데이터를 꺼낼 때 사용하는 메소드의 이름을 getXXX로 시작하지 않고 필드 이름과 똑같은 이름의 메소드를 생성한다.
// record 방식의 경우, 모든 필드가 final로 선언되어 불변의 객체가 되며, 전체생성자나 getter, ...을 알아서 만들어줌.
// @Builder 방식을 사용할 경우, 생성자 방식에 비해 가독성이 좋음.

@Builder
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
public record CreatePostRequest(

    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    @Schema(description = "게시글 제목", example = "1주차 세션: 기초 GitHub 다루기")
    String title,

    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    @Schema(description = "게시글 내용", example = "GitHub를 배워요")
    String content
) {}

// 이전 코드 (class 방식)
// @AllArgsConstructor
// @Getter
// @Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
// public class CreatePostRequest {
//
//     @NotBlank(message = "제목은 비어있을 수 없습니다.")
//     @Schema(description = "게시글 제목", example = "1주차 세션: 기초 GitHub 다루기")
//     private String title;
//
//     @NotBlank(message = "내용은 비어있을 수 없습니다.")
//     @Schema(description = "게시글 내용", example = "GitHub를 배워요")
//     private String content;
// }
