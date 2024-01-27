package com.example.sosikmemberservice.dto.response;

import com.example.sosikmemberservice.model.Member;
import lombok.Builder;

@Builder
public record ResponseAuth(String refreshToken, String accessToken, Member member) {
    public static ResponseAuth buildResponseAuth(String refreshToken, String accessToken, Member member) {
        return ResponseAuth.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .member(member)
                .build();
    }
}
