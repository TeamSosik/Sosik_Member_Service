package com.example.sosikmemberservice.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResult {

    private HttpStatus code;
    private String message;

    @Builder
    public ErrorResult(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
