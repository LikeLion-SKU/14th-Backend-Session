package com.example.likelionbe.domain.listing.controller;

import com.example.likelionbe.domain.listing.dto.CreateListingReqDto;
import com.example.likelionbe.domain.listing.dto.ListingDetailResDto;
import com.example.likelionbe.domain.listing.dto.ListingSummaryResDto;
import com.example.likelionbe.domain.listing.dto.UpdateListingReqDto;
import com.example.likelionbe.global.common.BaseResponse;
import com.example.likelionbe.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Listing", description = "매물 관련 API")
@RequestMapping("/api/listings")
public interface ListingControllerDocs {

    @Operation(summary = "매물 목록 조회", description = "DELETED 상태를 제외한 전체 매물 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "매물 목록 조회 성공")
    })
    @GetMapping
    ResponseEntity<BaseResponse<List<ListingSummaryResDto>>> getListingList();

    @Operation(summary = "매물 상세 조회", description = "매물 ID로 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "매물 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "매물을 찾을 수 없습니다.")
    })
    @GetMapping("/{listingId}")
    ResponseEntity<BaseResponse<ListingDetailResDto>> getListing(@PathVariable Long listingId);

    @Operation(summary = "매물 등록", description = "새로운 매물을 등록합니다. (판매자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "매물 등록 성공"),
            @ApiResponse(responseCode = "403", description = "판매자만 접근할 수 있습니다.")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    ResponseEntity<BaseResponse<ListingDetailResDto>> createListing(
            @Valid @RequestBody CreateListingReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "매물 수정", description = "매물 정보를 수정합니다. (본인 매물만 가능)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "매물 수정 성공"),
            @ApiResponse(responseCode = "403", description = "해당 매물의 소유자가 아닙니다."),
            @ApiResponse(responseCode = "404", description = "매물을 찾을 수 없습니다.")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{listingId}")
    ResponseEntity<BaseResponse<ListingDetailResDto>> updateListing(
            @PathVariable Long listingId,
            @Valid @RequestBody UpdateListingReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "매물 삭제", description = "매물을 소프트 삭제합니다. (본인 매물만 가능)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "매물 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "해당 매물의 소유자가 아닙니다."),
            @ApiResponse(responseCode = "404", description = "매물을 찾을 수 없습니다.")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{listingId}")
    ResponseEntity<BaseResponse<Void>> deleteListing(
            @PathVariable Long listingId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
