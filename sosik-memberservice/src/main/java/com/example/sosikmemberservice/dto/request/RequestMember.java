package com.example.sosikmemberservice.dto.request;


import lombok.Builder;


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
