package com.example.sosikmemberservice.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestSignup( @NotNull(message = "Please enter in email format")
                             @jakarta.validation.constraints.Email
                             String email,
                             @NotNull
                             @Size(min = 8,max = 16 ,
                                   message = "Password must be at least 8 characters and not more than 16 characters.")
                             String password,
                             String name,
                             String gender,
                             BigDecimal height,
                             Integer activityLevel,
                             String nickname,
                             String birthday,
                             Integer tdeeCalculation,
                             BigDecimal currentWeight,
                             BigDecimal targetWeight

)
{


}
