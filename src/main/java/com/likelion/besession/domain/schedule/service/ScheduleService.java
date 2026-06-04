package com.likelion.besession.domain.schedule.service;

import com.likelion.besession.domain.schedule.dto.request.CreateScheduleRequest;
import com.likelion.besession.domain.schedule.dto.response.CheckListResponse;
import com.likelion.besession.domain.schedule.dto.response.ScheduleResponse;
import com.likelion.besession.domain.schedule.dto.response.ScheduleTimelineResponse;
import com.likelion.besession.domain.schedule.entity.CheckList;
import com.likelion.besession.domain.schedule.entity.Schedule;
import com.likelion.besession.domain.schedule.exception.ScheduleErrorCode;
import com.likelion.besession.domain.schedule.repository.CheckListRepository;
import com.likelion.besession.domain.schedule.repository.ScheduleRepository;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;

    // 일정 생성
    public ScheduleResponse createSchedule(Long userId, CreateScheduleRequest request) {
        User user = getUser(userId);
        Schedule schedule = Schedule.builder()
                .user(user)
                .stage(request.getStage())
                .title(request.getTitle())
                .date(request.getDate())
                .warningAt(request.getWarningAt())
                .build();
        return ScheduleResponse.from(scheduleRepository.save(schedule));
    }

    // 내 일정 목록 조회: 날짜 오름차순 정렬 (가장 가까운 일정부터)
    @Transactional(readOnly = true)
    public List<ScheduleResponse> getMySchedules(Long userId) {
        User user = getUser(userId);
        return scheduleRepository.findAllByUserOrderByDateAsc(user)
                .stream()
                .map(ScheduleResponse::from)
                .toList();
    }

    // 타임라인 조회: 일정 정보 + 체크리스트 진행률 함께 반환
    @Transactional(readOnly = true)
    public ScheduleTimelineResponse getTimeline(Long userId, Long scheduleId) {
        Schedule schedule = getScheduleByIdAndUser(userId, scheduleId);
        return ScheduleTimelineResponse.from(schedule);
    }

    // 체크리스트 상태 토글: checked → unchecked, unchecked → checked
    public CheckListResponse toggleCheckList(Long userId, Long scheduleId, Long checkListId) {
        // 일정이 본인 것인지 검증
        Schedule schedule = getScheduleByIdAndUser(userId, scheduleId);
        // 체크리스트가 해당 일정에 속하는지 검증
        CheckList checkList = checkListRepository.findByIdAndSchedule(checkListId, schedule)
                .orElseThrow(() -> new CustomException(ScheduleErrorCode.CHECKLIST_NOT_FOUND));
        checkList.toggleChecked();
        return CheckListResponse.from(checkList);
    }

    // 일정 조회 공통 메서드: 본인 소유 여부 검증 포함
    private Schedule getScheduleByIdAndUser(Long userId, Long scheduleId) {
        User user = getUser(userId);
        return scheduleRepository.findByIdAndUser(scheduleId, user)
                .orElseThrow(() -> new CustomException(ScheduleErrorCode.SCHEDULE_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
