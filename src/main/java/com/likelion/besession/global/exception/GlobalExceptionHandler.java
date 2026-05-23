package com.likelion.besession.global.exception;

import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
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

    // 지원하지 않는 HTTP 메소드로 요청이 들어옴
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        // 요청 가능한 메소드 형식들
        String supportedHttpMethods = Arrays.toString(ex.getSupportedMethods());
        // 실제 요청 받은 메소드
        String requestedMethod = ex.getMethod();

        String errorMessages = "(실제 요청 메소드 : " + requestedMethod + ") " + "(요청 가능한 메소드 목록 : " +  supportedHttpMethods + ")";

        log.warn("지원하지 않는 HTTP 메소드 요청 발생 : {}", errorMessages);
        return ResponseEntity.badRequest().body(BaseResponse.error(GlobalErrorCode.METHOD_NOT_ALLOWED.getCode(), GlobalErrorCode.METHOD_NOT_ALLOWED.getMessage()));
    }

    // 유효하지 않은 엔드포인트 접근
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseResponse<?>> noHandlerFoundException(
            NoHandlerFoundException ex) {

        log.warn("유효하지 않은 엔드포인트 : {}", ex.getRequestURL());

        return ResponseEntity.badRequest().body(BaseResponse.error(GlobalErrorCode.HANDLE_NOT_FOUND.getCode(), GlobalErrorCode.HANDLE_NOT_FOUND.getMessage()));
    }

    // 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {
        log.error("Server 오류 발생: ", ex);
        return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(BaseResponse.error(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(), GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }

//    @ExceptionHandler(SQLException.class)
//    public ResponseEntity<BaseResponse<?>> handleSQLException(SQLException ex) {
//
//    }
}
