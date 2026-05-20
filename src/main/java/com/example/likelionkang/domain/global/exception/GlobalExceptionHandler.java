package com.example.likelionkang.domain.global.exception;

import com.example.likelionkang.domain.global.common.BaseResponse;
import com.example.likelionkang.domain.global.exception.model.BaseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    // JSON 형식 오류

    @ExceptionHandler(HttpMessageNotReadableException.class)

    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(

            HttpMessageNotReadableException ex) {

        log.warn("JSON 파싱 오류 발생: {}", ex.getMessage());

        return ResponseEntity.badRequest()

                .body(BaseResponse.error(

                        GlobalErrorCode.INVALID_INPUT_VALUE.getCode(),

                        "잘못된 JSON 요청 형식입니다."

                ));

    }

    // PathVariable / RequestParam 타입 불일치

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)

    public ResponseEntity<BaseResponse<?>> handleMethodArgumentTypeMismatchException(

            MethodArgumentTypeMismatchException ex) {

        log.warn("타입 불일치 오류 발생: {}", ex.getMessage());

        return ResponseEntity.badRequest()

                .body(BaseResponse.error(

                        GlobalErrorCode.INVALID_INPUT_VALUE.getCode(),

                        "요청 파라미터 타입이 올바르지 않습니다."

                ));

    }

    // 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {
        log.error("Server 오류 발생: ", ex);
        return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(BaseResponse.error(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(), GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}

