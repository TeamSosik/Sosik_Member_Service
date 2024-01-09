package com.example.sosikmemberservice.dto.response;

import com.example.sosikmemberservice.model.entity.WeightEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class GetWeight {

    private Long id;
    private BigDecimal currentWeight;
    private BigDecimal targetWeight;
    private String createdAt;


    @Builder
    public GetWeight(Long id, BigDecimal currentWeight, BigDecimal targetWeight, String createdAt) {
        this.id = id;
        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
        this.createdAt = createdAt;

    }

    public static GetWeight create(WeightEntity weight) {
        return GetWeight.builder()
                .id(weight.getId())
                .currentWeight(weight.getCurrentWeight())
                .targetWeight(weight.getTargetWeight())
                .createdAt(weight.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }


}
