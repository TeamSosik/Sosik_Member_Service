package com.example.sosikmemberservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT,"이미 사용중인 회원입니다!"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"유저를 찾지 못했습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"유효하지 않는 패스워드입니다"),

    //token
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED,"토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED,"만료된 토큰입니다."),
    UNSUPPORTED_TOKEN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"지원하지 않는 토큰입니다."),
    MALFORMED_TOKEN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"올바르지 않는 토큰입니다."),

    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED,"권한이 없는 사용자입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"내부 서버의 오류입니다."),
    NAME_LENGTH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"이름의 길이가 너무 깁니다"),
    NAME_BLANK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"이름은 공백일 수 없습니다!"),
    EMAIL_VALIDATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"이메일 형식을  확인해주세요!");

    private HttpStatus status;
    private String message;
}
