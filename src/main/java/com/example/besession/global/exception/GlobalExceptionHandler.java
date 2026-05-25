package com.example.besession.global.exception;

import com.example.besession.global.common.BaseResponse;
import com.example.besession.global.exception.model.BaseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(
            CustomException ex) {

        BaseErrorCode errorCode = ex.getErrorCode();

        log.warn("CustomException 발생: {} - {}",
                errorCode.getCode(),
                errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(BaseResponse.error(
                        errorCode.getCode(),
                        errorCode.getMessage()
                ));
    }

    // Validation 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMessages =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> String.format(
                                "[%s] %s",
                                error.getField(),
                                error.getDefaultMessage()
                        ))
                        .collect(Collectors.joining(" / "));

        log.warn("Validation 오류 발생: {}", errorMessages);

        return ResponseEntity
                .badRequest()
                .body(BaseResponse.error(
                        GlobalErrorCode.INVALID_INPUT_VALUE.getCode(),
                        errorMessages
                ));
    }

    // 잘못된 JSON 요청
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {

        log.warn("JSON 형식 오류", ex);

        return ResponseEntity
                .status(GlobalErrorCode.INVALID_JSON_FORMAT.getStatus())
                .body(BaseResponse.error(
                        GlobalErrorCode.INVALID_JSON_FORMAT.getCode(),
                        GlobalErrorCode.INVALID_JSON_FORMAT.getMessage()
                ));
    }

    // 지원하지 않는 HTTP Method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodNotAllowedException(
            HttpRequestMethodNotSupportedException ex) {

        log.warn("지원되지 않는 HTTP Method 요청: {}", ex.getMethod());

        return ResponseEntity
                .status(GlobalErrorCode.METHOD_NOT_ALLOWED.getStatus())
                .body(BaseResponse.error(
                        GlobalErrorCode.METHOD_NOT_ALLOWED.getCode(),
                        GlobalErrorCode.METHOD_NOT_ALLOWED.getMessage()
                ));
    }

    // 예상치 못한 서버 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {

        log.error("서버 내부 오류 발생", ex);

        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(BaseResponse.error(
                        GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()
                ));
    }
}