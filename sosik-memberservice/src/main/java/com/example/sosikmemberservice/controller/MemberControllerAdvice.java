package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorResult;
import com.example.sosikmemberservice.exception.FieldErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MemberControllerAdvice {

    // 파라미터 검증 예외
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<FieldErrorResult> MethodArgumentNotValidEx(MethodArgumentNotValidException e) {

        FieldErrorResult body = FieldErrorResult.builder()
                .field(e.getFieldError().getField())
                .code(e.getStatusCode())
                .message(e.getFieldError().getDefaultMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(value = {ApplicationException.class})
    public ResponseEntity<ErrorResult> ApplicationEx(ApplicationException e) {

        ErrorResult body = ErrorResult.builder()
                .code(e.getErrorCode().getStatus())
                .message(e.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(body);

    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorResult> RuntimeEx(RuntimeException e) {

        ErrorResult body = ErrorResult.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);

    }






}
