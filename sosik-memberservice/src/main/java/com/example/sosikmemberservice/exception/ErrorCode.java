package com.example.sosikmemberservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //user
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "You are an unauthorized user."),
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "You are already a member!"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user found."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Please check your ID or password"),

    //token
    EXPIRED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "This token is expired."),
    UNSUPPORTED_TOKEN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "This token is not supported."),
    MALFORMED_TOKEN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid token."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "This is an error from the internal server.");

    private HttpStatus status;
    private String message;
}
