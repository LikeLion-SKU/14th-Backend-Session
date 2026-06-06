package com.example.likelionbe.domain.contract.enums;

public enum ContractStatus {
    REQUESTED,   // 구매자 신청 (계약전)
    APPROVED,    // 판매자 승인 (계약중)
    REJECTED,    // 판매자 거절
    COMPLETED,   // 계약 완료 (계약후)
    CANCELLED    // 취소
}
