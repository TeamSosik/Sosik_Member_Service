package com.example.sosikmemberservice.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestWeight(
        BigDecimal currentWeight,
        BigDecimal targetWeight
) {
}
