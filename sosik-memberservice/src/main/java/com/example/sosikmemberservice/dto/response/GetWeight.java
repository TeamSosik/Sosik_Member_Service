package com.example.sosikmemberservice.dto.response;

import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.model.entity.WeightEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GetWeight {

    private Long id;
    private BigDecimal currentWeight;
    private BigDecimal goalWeight;

    @Builder
    public GetWeight(Long id, BigDecimal currentWeight, BigDecimal goalWeight) {
        this.id = id;
        this.currentWeight = currentWeight;
        this.goalWeight = goalWeight;
    }

    public static GetWeight create(WeightEntity weight) {
        return GetWeight.builder()
                .id(weight.getId())
                .currentWeight(weight.getCurrentWeight())
                .goalWeight(weight.getGoalWeight())
                .build();
    }


}
