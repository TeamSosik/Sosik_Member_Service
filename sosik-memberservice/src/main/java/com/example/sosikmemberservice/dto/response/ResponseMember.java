package com.example.sosikmemberservice.dto.response;

import com.example.sosikmemberservice.dto.request.RequestMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMember {
    private String email;
    private String name;
    private String gender;
    private Integer activityLevel;




}
