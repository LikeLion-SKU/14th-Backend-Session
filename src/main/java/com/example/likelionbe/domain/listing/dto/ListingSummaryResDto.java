package com.example.likelionbe.domain.listing.dto;

import com.example.likelionbe.domain.listing.enums.ListingStatus;
import com.example.likelionbe.domain.listing.enums.PropertyType;
import com.example.likelionbe.domain.listing.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "ListingSummaryResDto: 매물 목록 응답 DTO")
public record ListingSummaryResDto(
        @Schema(description = "매물 ID", example = "1")
        Long listingId,

        @Schema(description = "매물 제목", example = "역세권 원룸 급매")
        String title,

        @Schema(description = "주소", example = "서울시 강남구 역삼동 123-45")
        String address,

        @Schema(description = "거래 유형", example = "SALE")
        TransactionType transactionType,

        @Schema(description = "매물 유형", example = "APARTMENT")
        PropertyType propertyType,

        @Schema(description = "매물 상태", example = "AVAILABLE")
        ListingStatus listingStatus,

        @Schema(description = "보증금", example = "10000000")
        Long deposit,

        @Schema(description = "월세", example = "500000")
        Long monthlyRent,

        @Schema(description = "매매가", example = "500000000")
        Long salePrice,

        @Schema(description = "등록일시", example = "2025-01-01T00:00:00")
        LocalDateTime createdAt
) {
}
