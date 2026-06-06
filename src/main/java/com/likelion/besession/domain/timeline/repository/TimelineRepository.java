package com.likelion.besession.domain.timeline.repository;

import com.likelion.besession.domain.contract.entity.ContractStatus;
import com.likelion.besession.domain.timeline.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findByContractIdAndStageOrderByTimelineOrderAsc(Long contractId, ContractStatus stage);
}
