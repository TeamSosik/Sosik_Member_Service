package com.example.sosikmemberservice.dto.response;

import com.example.sosikmemberservice.model.entity.MemberEntity;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ResponseGetManagementData(Integer tdeeCalculation,
                                        BigDecimal currentWeight,
                                        BigDecimal targetWeight,
                                        Integer managementWeek
) {
    public static ResponseGetManagementData buildResponseGetManagementData(MemberEntity memberEntity) {
        return ResponseGetManagementData.builder()
                .tdeeCalculation(memberEntity.getTdeeCalculation())
                .currentWeight(MemberEntity.getLastWeightEntity(memberEntity).getCurrentWeight())
                .targetWeight(MemberEntity.getLastWeightEntity(memberEntity).getTargetWeight())
                .managementWeek(MemberEntity.getLastWeightEntity(memberEntity).getManagementWeek())
                .build();
    }

}
