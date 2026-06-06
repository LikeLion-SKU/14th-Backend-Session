package com.example.likelionbe.domain.listing.exception;

import com.example.likelionbe.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ListingErrorCode implements BaseErrorCode {
    LISTING_NOT_FOUND("L001", "매물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    LISTING_NOT_OWNER("L002", "해당 매물의 소유자가 아닙니다.", HttpStatus.FORBIDDEN),
    LISTING_ALREADY_DELETED("L003", "이미 삭제된 매물입니다.", HttpStatus.BAD_REQUEST),
    LISTING_NOT_MODIFIABLE("L004", "수정할 수 없는 상태의 매물입니다.", HttpStatus.BAD_REQUEST),
    LISTING_NOT_DELETABLE("L005", "삭제할 수 없는 상태의 매물입니다. 진행 중인 계약이 있는지 확인해주세요.", HttpStatus.BAD_REQUEST),
    LISTING_SELLER_ONLY("L006", "판매자만 접근할 수 있습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
