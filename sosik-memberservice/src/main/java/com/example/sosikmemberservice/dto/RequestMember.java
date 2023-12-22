package com.example.sosikmemberservice.dto.request;


import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.model.vo.Name;
import com.example.sosikmemberservice.model.vo.ProfileImageUrl;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
public record RequestMember(String email,
                            String password,
                            String name,
                            String gender,
                            BigDecimal height,
                            Integer activityLevel,
                            String nickname,
                            String profileImage,
                            String birthday,
                            Integer tdeeCalculation

)
{


}
