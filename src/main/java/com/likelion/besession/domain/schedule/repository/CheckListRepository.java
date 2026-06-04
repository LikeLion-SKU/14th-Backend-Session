package com.likelion.besession.domain.schedule.repository;

import com.likelion.besession.domain.schedule.entity.CheckList;
import com.likelion.besession.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    // 체크리스트 ID와 일정을 함께 검증해서 해당 일정에 속한 항목만 조회
    Optional<CheckList> findByIdAndSchedule(Long id, Schedule schedule);
}
