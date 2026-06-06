package com.example.likelionbe.domain.listing.dto;

import com.example.likelionbe.domain.listing.enums.ListingStatus;
import com.example.likelionbe.domain.listing.enums.PropertyType;
import com.example.likelionbe.domain.listing.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Schema(title = "ListingDetailResDto: 매물 상세 응답 DTO")
public record ListingDetailResDto(
        @Schema(description = "매물 ID", example = "1")
        Long listingId,

        @Schema(description = "판매자 ID", example = "1")
        Long sellerId,

        @Schema(description = "매물 제목", example = "역세권 원룸 급매")
        String title,

        @Schema(description = "매물 설명", example = "강남역 도보 5분 거리 원룸입니다.")
        String description,

        @Schema(description = "매물 유형", example = "APARTMENT")
        PropertyType propertyType,

        @Schema(description = "거래 유형", example = "SALE")
        TransactionType transactionType,

        @Schema(description = "매물 상태", example = "AVAILABLE")
        ListingStatus listingStatus,

        @Schema(description = "매매가", example = "500000000")
        Long salePrice,

        @Schema(description = "보증금", example = "10000000")
        Long deposit,

        @Schema(description = "월세", example = "500000")
        Long monthlyRent,

        @Schema(description = "주소", example = "서울시 강남구 역삼동 123-45")
        String address,

        @Schema(description = "상세 주소", example = "101동 202호")
        String detailAddress,

        @Schema(description = "전용 면적 (㎡)", example = "59.99")
        BigDecimal exclusiveArea,

        @Schema(description = "공급 면적 (㎡)", example = "84.99")
        BigDecimal supplyArea,

        @Schema(description = "방 개수", example = "2")
        Integer roomCount,

        @Schema(description = "욕실 개수", example = "1")
        Integer bathroomCount,

        @Schema(description = "층", example = "5")
        Integer floor,

        @Schema(description = "전체 층수", example = "15")
        Integer totalFloor,

        @Schema(description = "썸네일 이미지 URL", example = "https://example.com/image.jpg")
        String thumbnailUrl,

        @Schema(description = "등록일시", example = "2025-01-01T00:00:00")
        LocalDateTime createdAt,

        @Schema(description = "수정일시", example = "2025-01-02T00:00:00")
        LocalDateTime modifiedAt
) {
}
