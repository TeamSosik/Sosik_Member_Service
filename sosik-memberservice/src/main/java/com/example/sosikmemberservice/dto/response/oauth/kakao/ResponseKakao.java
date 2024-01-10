package com.example.sosikmemberservice.dto.response.oauth.kakao;

import lombok.Builder;

@Builder
public record ResponseKakao(ResponseKakaoToken token,
                            ResponseKakaoUserInfo info) {
}
