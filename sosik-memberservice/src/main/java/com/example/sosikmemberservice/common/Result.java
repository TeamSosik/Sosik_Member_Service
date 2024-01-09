package com.example.sosikmemberservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result<T> {
    private String resultCode;
    private T result;

    public static Result<Void> success() {
        return new Result<Void>("Success", null);
    }

    public static <T> Result<T> success(T result) {
        return new Result<T>("Success", result);
    }


}
