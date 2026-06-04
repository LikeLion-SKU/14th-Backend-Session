package com.likelion.besession.domain.schedule.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScheduleErrorCode implements BaseErrorCode {

    SCHEDULE_NOT_FOUND("SCHEDULE4041", "일정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),              // 존재하지 않거나 본인 일정이 아닌 경우
    CHECKLIST_NOT_FOUND("SCHEDULE4042", "체크리스트 항목을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);   // 해당 일정에 속하지 않는 체크리스트 접근 시

    private final String code;
    private final String message;
    private final HttpStatus status;
}
