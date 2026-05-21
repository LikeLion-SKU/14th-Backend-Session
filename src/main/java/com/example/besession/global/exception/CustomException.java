package com.example.besession.global.exception;

import com.example.besession.global.exception.model.BaseErrorCode;

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
