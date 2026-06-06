package com.likelion.besession.domain.schedule.service;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.exception.ContractNotFoundException;
import com.likelion.besession.domain.contract.repository.ContractRepository;
import com.likelion.besession.domain.schedule.dto.request.ScheduleCreateRequest;
import com.likelion.besession.domain.schedule.dto.response.MyScheduleResponse;
import com.likelion.besession.domain.schedule.dto.response.ScheduleResponse;
import com.likelion.besession.domain.schedule.entity.ContractSchedule;

import com.likelion.besession.domain.schedule.repository.ContractScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ContractScheduleRepository scheduleRepository;
    private final ContractRepository contractRepository;

    public MyScheduleResponse getSchedules(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(ContractNotFoundException::new);

        List<ScheduleResponse> schedules =
            scheduleRepository.findByContractContractId(contractId).stream()
                .map(ScheduleResponse::from)
                .toList();

        return MyScheduleResponse.of(contract, schedules);
    }

    @Transactional
    public ScheduleResponse createSchedule(Long contractId, ScheduleCreateRequest request) {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(ContractNotFoundException::new);

        ContractSchedule schedule = ContractSchedule.builder()
            .contract(contract)
            .title(request.getTitle())
            .phase(request.getPhase())
            .build();

        return ScheduleResponse.from(scheduleRepository.save(schedule));
    }
}
