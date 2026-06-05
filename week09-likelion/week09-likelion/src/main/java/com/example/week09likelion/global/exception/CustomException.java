package com.example.week09likelion.global.exception;

import com.example.week09likelion.global.exception.model.BaseErrorCode;

public class CustomException extends RuntimeException {
    private  final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }
}
