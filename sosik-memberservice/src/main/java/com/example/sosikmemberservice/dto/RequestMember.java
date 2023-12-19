package com.example.sosikmemberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestMember {
    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String gender;
    private BigDecimal height;
    private Integer activityLevel;
    private String nickname;
    private String profileImage;
    private String birthday;
}
