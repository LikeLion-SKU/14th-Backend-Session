package com.example.likelionbe.domain.listing.entity;

import com.example.likelionbe.domain.listing.dto.UpdateListingReqDto;
import com.example.likelionbe.domain.listing.enums.ListingStatus;
import com.example.likelionbe.domain.listing.enums.PropertyType;
import com.example.likelionbe.domain.listing.enums.TransactionType;
import com.example.likelionbe.domain.user.entity.User;
import com.example.likelionbe.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "listing")
public class Listing extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ListingStatus listingStatus = ListingStatus.AVAILABLE;

    private Long salePrice;

    private Long deposit;

    private Long monthlyRent;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(length = 255)
    private String detailAddress;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal exclusiveArea;

    @Column(precision = 10, scale = 2)
    private BigDecimal supplyArea;

    @Column(nullable = false)
    private Integer roomCount;

    @Column(nullable = false)
    private Integer bathroomCount;

    private Integer floor;

    private Integer totalFloor;

    @Column(length = 500)
    private String thumbnailUrl;

    private LocalDateTime deletedAt;

    /**
     * 매물 정보를 업데이트합니다.
     *
     * @param dto 업데이트할 매물 정보
     */
    public void updateListing(UpdateListingReqDto dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.salePrice = dto.salePrice();
        this.deposit = dto.deposit();
        this.monthlyRent = dto.monthlyRent();
        this.address = dto.address();
        this.detailAddress = dto.detailAddress();
        this.exclusiveArea = dto.exclusiveArea();
        this.supplyArea = dto.supplyArea();
        this.roomCount = dto.roomCount();
        this.bathroomCount = dto.bathroomCount();
        this.floor = dto.floor();
        this.totalFloor = dto.totalFloor();
        this.thumbnailUrl = dto.thumbnailUrl();
        this.propertyType = dto.propertyType();
        this.transactionType = dto.transactionType();
    }

    /**
     * 매물을 소프트 삭제 처리합니다.
     */
    public void softDelete() {
        this.listingStatus = ListingStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    /**
     * 매물 상태를 변경합니다.
     *
     * @param status 변경할 상태
     */
    public void updateStatus(ListingStatus status) {
        this.listingStatus = status;
    }
}
