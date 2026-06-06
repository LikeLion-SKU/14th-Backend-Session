package com.example.likelionbe.domain.listing.enums;

public enum ListingStatus {
    AVAILABLE,        // 공개 중, 신청 가능
    CONTRACT_PENDING, // 승인된 계약 진행 중
    CONTRACTED,       // 최종 계약 완료
    DELETED           // 삭제 처리
}
