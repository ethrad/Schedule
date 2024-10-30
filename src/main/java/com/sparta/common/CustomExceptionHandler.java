package com.sparta.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(ApplicationException e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
}