package com.example.sosikmemberservice.model.vo;

import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Name {
    public static final int MAX_LENGTH = 50;


    @Column(name = "name", nullable = false, length = MAX_LENGTH)
    private String value;

    public Name(final String value){
        validateNull(value);
        final String trimmedValue = value.trim();
        validate(trimmedValue);
        this.value = trimmedValue;
    }

    private void validateNull(final String value) {
        if(Objects.isNull(value)){
            throw new NullPointerException("이름은 공백일 수 없습니다!");
        }
    }

    private void validate(String trimmedValue) {
        if(trimmedValue.length() >MAX_LENGTH){
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public Name change(final String name){
        return new Name(name);
    }


}
