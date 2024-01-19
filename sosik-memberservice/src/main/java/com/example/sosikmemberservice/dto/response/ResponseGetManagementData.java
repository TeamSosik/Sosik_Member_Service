package com.example.sosikmemberservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ResponseGetManagementData(Integer tdeeCalculation,
                                        BigDecimal currentWeight,
                                        BigDecimal targetWeight,
                                        Integer managementWeek
                                        ) {

}
