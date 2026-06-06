package com.example.likelionbe.domain.listing.dto;

import com.example.likelionbe.domain.listing.enums.PropertyType;
import com.example.likelionbe.domain.listing.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(title = "CreateListingReqDto: 매물 생성 요청 DTO")
public record CreateListingReqDto(
        @NotBlank(message = "제목은 필수 입력값입니다.")
        @Schema(description = "매물 제목", example = "역세권 원룸 급매")
        String title,

        @NotBlank(message = "설명은 필수 입력값입니다.")
        @Schema(description = "매물 설명", example = "강남역 도보 5분 거리 원룸입니다.")
        String description,

        @NotNull(message = "매물 유형은 필수 입력값입니다.")
        @Schema(description = "매물 유형", example = "APARTMENT")
        PropertyType propertyType,

        @NotNull(message = "거래 유형은 필수 입력값입니다.")
        @Schema(description = "거래 유형", example = "SALE")
        TransactionType transactionType,

        @Schema(description = "매매가", example = "500000000")
        Long salePrice,

        @Schema(description = "보증금", example = "10000000")
        Long deposit,

        @Schema(description = "월세", example = "500000")
        Long monthlyRent,

        @NotBlank(message = "주소는 필수 입력값입니다.")
        @Schema(description = "주소", example = "서울시 강남구 역삼동 123-45")
        String address,

        @Schema(description = "상세 주소", example = "101동 202호")
        String detailAddress,

        @NotNull(message = "전용 면적은 필수 입력값입니다.")
        @Schema(description = "전용 면적 (㎡)", example = "59.99")
        BigDecimal exclusiveArea,

        @Schema(description = "공급 면적 (㎡)", example = "84.99")
        BigDecimal supplyArea,

        @NotNull(message = "방 개수는 필수 입력값입니다.")
        @Schema(description = "방 개수", example = "2")
        Integer roomCount,

        @NotNull(message = "욕실 개수는 필수 입력값입니다.")
        @Schema(description = "욕실 개수", example = "1")
        Integer bathroomCount,

        @Schema(description = "층", example = "5")
        Integer floor,

        @Schema(description = "전체 층수", example = "15")
        Integer totalFloor,

        @Schema(description = "썸네일 이미지 URL", example = "https://example.com/image.jpg")
        String thumbnailUrl
) {
}
