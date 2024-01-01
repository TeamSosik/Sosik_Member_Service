package com.example.sosikmemberservice.dto.response;

import lombok.Builder;

@Builder
public record ResponseAuth(String refreshToken,String accessToken) {
}
