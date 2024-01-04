package com.example.sosikmemberservice.dto.response;

import com.example.sosikmemberservice.model.Member;
import lombok.Builder;

@Builder
public record ResponseAuth(String refreshToken, String accessToken, Member member) {
}
