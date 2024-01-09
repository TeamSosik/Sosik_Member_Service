package com.example.sosikmemberservice.dto.request;

import com.example.sosikmemberservice.model.entity.MemberEntity;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestWeight(
        MemberEntity member,
        BigDecimal currentWeight,
        BigDecimal targetWeight
) {
}
