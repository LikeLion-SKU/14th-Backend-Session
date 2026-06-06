package com.example.likelionbe.domain.listing.controller;

import com.example.likelionbe.domain.listing.dto.CreateListingReqDto;
import com.example.likelionbe.domain.listing.dto.ListingDetailResDto;
import com.example.likelionbe.domain.listing.dto.ListingSummaryResDto;
import com.example.likelionbe.domain.listing.dto.UpdateListingReqDto;
import com.example.likelionbe.domain.listing.service.ListingService;
import com.example.likelionbe.global.common.BaseResponse;
import com.example.likelionbe.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ListingController implements ListingControllerDocs {

    private final ListingService listingService;

    @Override
    public ResponseEntity<BaseResponse<List<ListingSummaryResDto>>> getListingList() {
        List<ListingSummaryResDto> listingList = listingService.getListingList();
        return ResponseEntity.ok(BaseResponse.success(listingList));
    }

    @Override
    public ResponseEntity<BaseResponse<ListingDetailResDto>> getListing(Long listingId) {
        ListingDetailResDto listing = listingService.getListing(listingId);
        return ResponseEntity.ok(BaseResponse.success(listing));
    }

    @Override
    public ResponseEntity<BaseResponse<ListingDetailResDto>> createListing(
            @Valid @RequestBody CreateListingReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        ListingDetailResDto listing = listingService.createListing(reqDto, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "매물 등록에 성공했습니다.", listing));
    }

    @Override
    public ResponseEntity<BaseResponse<ListingDetailResDto>> updateListing(
            Long listingId,
            @Valid @RequestBody UpdateListingReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        ListingDetailResDto listing = listingService.updateListing(listingId, reqDto, userId);
        return ResponseEntity.ok(BaseResponse.success("매물 수정에 성공했습니다.", listing));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> deleteListing(
            Long listingId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        listingService.deleteListing(listingId, userId);
        return ResponseEntity.ok(BaseResponse.success("매물 삭제에 성공했습니다.", null));
    }
}
