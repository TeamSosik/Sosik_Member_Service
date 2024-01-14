package com.example.sosikmemberservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestUpdateOAuthMember(@NotNull
                                       BigDecimal currentWeight,
                                       @NotNull
                                       BigDecimal targetWeight,
                                       @NotNull
                                       BigDecimal height,
                                       String gender,
                                       @NotNull
                                       String birthday,
                                       @NotNull
                                       Integer activityLevel,
                                       @NotNull
                                       Integer tdeeCalculation
) {
}