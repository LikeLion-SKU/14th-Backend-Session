package com.example.likelionbe.domain.contract.exception;

import com.example.likelionbe.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ContractErrorCode implements BaseErrorCode {
    CONTRACT_NOT_FOUND("C001", "계약을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CONTRACT_ALREADY_EXISTS("C002", "이미 해당 매물에 계약 신청이 존재합니다.", HttpStatus.CONFLICT),
    CONTRACT_NOT_BUYER("C003", "해당 계약의 구매자가 아닙니다.", HttpStatus.FORBIDDEN),
    CONTRACT_INVALID_STATUS_TRANSITION("C004", "잘못된 상태 변경입니다.", HttpStatus.BAD_REQUEST),
    CONTRACT_NOT_REQUESTED("C005", "신청 상태의 계약만 처리할 수 있습니다.", HttpStatus.BAD_REQUEST),
    CONTRACT_NOT_APPROVED("C006", "승인 상태의 계약만 완료할 수 있습니다.", HttpStatus.BAD_REQUEST),
    CONTRACT_LISTING_NOT_AVAILABLE("C007", "계약 가능한 상태의 매물이 아닙니다.", HttpStatus.BAD_REQUEST),
    CONTRACT_SELF_LISTING("C008", "본인 매물에는 계약 신청할 수 없습니다.", HttpStatus.BAD_REQUEST),
    CONTRACT_ALREADY_APPROVED("C009", "이미 승인된 계약이 존재합니다.", HttpStatus.CONFLICT),
    CONTRACT_DOCUMENT_NOT_READY("C010", "계약서 내용이 아직 준비되지 않았습니다.", HttpStatus.BAD_REQUEST),
    CHECKLIST_NOT_FOUND("C011", "체크리스트 항목을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CONTRACT_NOT_MODIFIABLE("C012", "수정할 수 없는 상태의 계약입니다.", HttpStatus.BAD_REQUEST),
    CONTRACT_BUYER_ONLY("C013", "구매자만 접근할 수 있습니다.", HttpStatus.FORBIDDEN),
    CONTRACT_SELLER_ONLY("C014", "판매자만 접근할 수 있습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
