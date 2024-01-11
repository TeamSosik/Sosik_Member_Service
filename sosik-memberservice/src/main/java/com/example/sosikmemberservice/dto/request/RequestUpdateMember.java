package com.example.sosikmemberservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record RequestUpdateMember(@NotNull
                                  BigDecimal currentWeight,
                                  @NotNull
                                  BigDecimal targetWeight,
                                  @NotNull
                                  BigDecimal height,
                                  @NotNull
                                  Integer activityLevel,
                                  @NotNull
                                  Integer tdeeCalculation
) {
}