package com.example.sosikmemberservice.dto.request;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record RequestUpdate(Long memberId,
                           Long weightId,
                           BigDecimal currentWeight,
                           BigDecimal targetWeight,
                           BigDecimal height,
                           Integer activityLevel,
                           String nickname,
                           String profileImage

) {
}