package com.likelion.besession.domain.timeline.service;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractStatus;
import com.likelion.besession.domain.contract.exception.ContractErrorCode;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.timeline.dto.request.CreateTimelineRequest;
import com.likelion.besession.domain.timeline.dto.request.UpdateTimelineRequest;
import com.likelion.besession.domain.timeline.dto.response.TimelineResponse;
import com.likelion.besession.domain.timeline.entity.Timeline;
import com.likelion.besession.domain.timeline.exception.TimelineErrorCode;
import com.likelion.besession.domain.timeline.repository.TimelineRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimelineService {

    private final TimelineRepository timelineRepository;
    private final ContractRepository contractRepository;

    public List<TimelineResponse> getTimelines(Long userId, Long contractId, String stage) {
        Contract contract = findContractById(contractId);
        validateOwner(contract, userId);

        ContractStatus contractStatus = parseStage(stage);
        List<Timeline> timelines = timelineRepository.findByContractIdAndStageOrderByTimelineOrderAsc(contractId, contractStatus);

        return timelines.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TimelineResponse createTimeline(Long userId, Long contractId, CreateTimelineRequest request) {
        Contract contract = findContractById(contractId);
        validateOwner(contract, userId);

        Timeline timeline = Timeline.builder()
                .title(request.title())
                .stage(parseStage(request.stage()))
                .timelineOrder(request.timelineOrder())
                .contract(contract)
                .build();

        Timeline saved = timelineRepository.save(timeline);

        return toResponse(saved);
    }

    @Transactional
    public TimelineResponse updateTimeline(Long userId, Long timelineId, UpdateTimelineRequest request) {
        Timeline timeline = findTimelineById(timelineId);
        validateOwner(timeline.getContract(), userId);

        timeline.update(
                request.title(),
                parseStage(request.stage()),
                request.timelineOrder()
        );

        return toResponse(timeline);
    }

    @Transactional
    public void deleteTimeline(Long userId, Long timelineId) {
        Timeline timeline = findTimelineById(timelineId);
        validateOwner(timeline.getContract(), userId);

        timelineRepository.delete(timeline);
    }

    private Contract findContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));
    }

    private Timeline findTimelineById(Long timelineId) {
        return timelineRepository.findById(timelineId)
                .orElseThrow(() -> new CustomException(TimelineErrorCode.TIMELINE_NOT_FOUND));
    }

    private void validateOwner(Contract contract, Long userId) {
        if (!contract.getUser().getId().equals(userId)) {
            throw new CustomException(TimelineErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    private ContractStatus parseStage(String stage) {
        try {
            return ContractStatus.valueOf(stage);
        } catch (IllegalArgumentException e) {
            throw new CustomException(TimelineErrorCode.INVALID_STAGE);
        }
    }

    private TimelineResponse toResponse(Timeline timeline) {
        return TimelineResponse.builder()
                .timelineId(timeline.getId())
                .title(timeline.getTitle())
                .stage(timeline.getStage().name())
                .timelineOrder(timeline.getTimelineOrder())
                .createdAt(timeline.getCreatedAt())
                .build();
    }
}
