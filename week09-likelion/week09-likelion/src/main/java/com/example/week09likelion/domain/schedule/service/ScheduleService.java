package com.example.week09likelion.domain.schedule.service;

import com.example.week09likelion.domain.contract.entity.Contract;
import com.example.week09likelion.domain.contract.exception.ContractErrorCode;
import com.example.week09likelion.domain.contract.repository.ContractRepository;
import com.example.week09likelion.domain.schedule.dto.request.ScheduleRequest;
import com.example.week09likelion.domain.schedule.dto.response.ScheduleResponse;
import com.example.week09likelion.domain.schedule.entity.ContractSchedule;
import com.example.week09likelion.domain.schedule.entity.ScheduleStage;
import com.example.week09likelion.domain.schedule.exception.ScheduleErrorCode;
import com.example.week09likelion.domain.schedule.repository.ContractScheduleRepository;
import com.example.week09likelion.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ContractRepository contractRepository;
    private final ContractScheduleRepository contractScheduleRepository;

    //  계약 일정 목록 조회
    public List<ScheduleResponse> getMySchedules(Long userId, ScheduleStage stage) {
        Contract contract = contractRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        List<ContractSchedule> schedules;

        if (stage == null) {
            schedules = contractScheduleRepository.findAllByContractId(contract.getId());
        } else {
            schedules = contractScheduleRepository.findAllByContractIdAndStage(contract.getId(), stage);
        }

        return schedules.stream()
                .map(ScheduleResponse::from)
                .toList();
    }

    //  계약 일정 등록
    @Transactional
    public ScheduleResponse createMySchedule(Long userId, ScheduleRequest request) {
        Contract contract = contractRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.CONTRACT_NOT_FOUND));

        ContractSchedule schedule = ContractSchedule.builder()
                .contract(contract)
                .stage(request.getStage())
                .title(request.getTitle())
                .dueDate(request.getDueDate())
                .build();

        ContractSchedule savedSchedule = contractScheduleRepository.save(schedule);

        return ScheduleResponse.from(savedSchedule);
    }

    //  계약 일정 수정
    @Transactional
    public ScheduleResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest request) {
        ContractSchedule schedule = contractScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ScheduleErrorCode.SCHEDULE_NOT_FOUND));

        if (!schedule.getContract().getUser().getId().equals(userId)) {
            throw new CustomException(ScheduleErrorCode.SCHEDULE_ACCESS_DENIED);
        }

        schedule.updateSchedule(
                request.getStage(),
                request.getTitle(),
                request.getDueDate(),
                request.getIsCompleted()
        );

        return ScheduleResponse.from(schedule);
    }
}