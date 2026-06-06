package com.example.likelionbe.domain.listing.repository;

import com.example.likelionbe.domain.listing.entity.Listing;
import com.example.likelionbe.domain.listing.enums.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    /**
     * DELETED 상태를 제외하고 createdAt 내림차순으로 매물 목록을 조회합니다.
     *
     * @param status 제외할 상태 (DELETED)
     * @return 매물 목록
     */
    List<Listing> findAllByListingStatusNotOrderByCreatedAtDesc(ListingStatus status);

    /**
     * ID와 DELETED 아닌 상태로 매물을 조회합니다.
     *
     * @param id     매물 ID
     * @param status 제외할 상태 (DELETED)
     * @return 매물 (Optional)
     */
    Optional<Listing> findByIdAndListingStatusNot(Long id, ListingStatus status);
}
