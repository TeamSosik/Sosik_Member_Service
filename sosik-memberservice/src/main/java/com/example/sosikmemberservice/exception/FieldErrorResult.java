package com.example.sosikmemberservice.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class FieldErrorResult {

    private final String field;
    private final HttpStatusCode code;
    private final String message;

    @Builder
    public FieldErrorResult(String field, HttpStatusCode code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }
}
