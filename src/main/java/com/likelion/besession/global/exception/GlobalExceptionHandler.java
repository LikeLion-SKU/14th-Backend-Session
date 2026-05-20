package com.likelion.besession.global.exception;

import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        log.warn("CustomException 발생: {} - {}", errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus())
                .body(BaseResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    // Validation 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex) {
        String errorMessages =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
                        .collect(Collectors.joining(" / "));
        log.warn("Validation 오류 발생: {}", errorMessages);
        return ResponseEntity.badRequest().body(BaseResponse.error(GlobalErrorCode.INVALID_INPUT_VALUE.getCode(), GlobalErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    // 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {
        log.error("Server 오류 발생: ", ex);
        return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(BaseResponse.error(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(), GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }

    // 지원하지 않는 HTTP Method 호출 시 발생 예외
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        log.warn("HttpRequestMethodNotSupportedException 발생: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(BaseResponse.error("G004", "지원하지 않는 HTTP 메서드 요청입니다."));
    }

    // Controller 메서드 인자 타입이 맞지 않을 때 발생 예외
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        log.warn("MethodArgumentTypeMismatchException 발생: {}", ex.getMessage());
        String message = String.format("요청 파라미터 타입 오류: [%s] 필드는 %s 타입이어야 합니다.",
                ex.getName(), ex.getRequiredType().getSimpleName());
        return ResponseEntity.badRequest()
                .body(BaseResponse.error(GlobalErrorCode.INVALID_INPUT_VALUE.getCode(), message));
    }
}
