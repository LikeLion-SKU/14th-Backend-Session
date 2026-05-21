    package com.likelion.besession.domain.user.exception;

    import com.likelion.besession.global.exception.model.BaseErrorCode;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import org.springframework.http.HttpStatus;

    @Getter
    @AllArgsConstructor
    public enum UserErrorCode implements BaseErrorCode {

        USER_NOT_FOUND("USER4041", "해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
        EMAIL_ALREADY_EXISTS("USER4091", "이미 존재하는 이메일입니다.", HttpStatus.CONFLICT);

        private final String code;
        private final String message;
        private final HttpStatus status;
    }