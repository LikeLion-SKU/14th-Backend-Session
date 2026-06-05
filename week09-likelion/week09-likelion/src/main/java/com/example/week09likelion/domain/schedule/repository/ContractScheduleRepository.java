package com.example.week09likelion.domain.schedule.repository;

import com.example.week09likelion.domain.schedule.entity.ContractSchedule;
import com.example.week09likelion.domain.schedule.entity.ScheduleStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractScheduleRepository extends JpaRepository<ContractSchedule, Long> {

    List<ContractSchedule> findAllByContractId(Long contractId);

    List<ContractSchedule> findAllByContractIdAndStage(Long contractId, ScheduleStage stage);
}