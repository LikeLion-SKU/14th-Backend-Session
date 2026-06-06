package com.likelion.besession.domain.schedule.repository;

import com.likelion.besession.domain.schedule.entity.ContractSchedule;
import com.likelion.besession.domain.schedule.entity.Phase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractScheduleRepository extends JpaRepository<ContractSchedule, Long> {

    List<ContractSchedule> findByContractContractIdAndPhase(Long contractId, Phase phase);

    List<ContractSchedule> findByContractContractId(Long contractId);
}
