package com.likelion.besession.domain.schedule.repository;

import com.likelion.besession.domain.schedule.entity.Schedule;
import com.likelion.besession.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 특정 사용자의 일정을 날짜 오름차순으로 전체 조회 (가장 가까운 일정부터)
    List<Schedule> findAllByUserOrderByDateAsc(User user);

    // 일정 ID와 사용자를 함께 검증해서 본인 일정만 조회
    Optional<Schedule> findByIdAndUser(Long id, User user);
}
