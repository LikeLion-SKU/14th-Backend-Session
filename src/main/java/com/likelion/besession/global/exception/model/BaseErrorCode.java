package com.likelion.besession.global.exception.model;

import org.springframework.http.HttpStatus;

/*
예외 발생 시, 어떤 코드, 메시지, 상태(HTTP)를 가질 것인지.
 */
public interface BaseErrorCode {

    String getCode();

    String getMessage();

    HttpStatus getStatus();

}
