package com.example.sosikmemberservice.dto.response.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ResponseKakaoUserInfo(Long id,
                                    @JsonProperty("has_signed_up")
                                    boolean hasSignedUp,
                                    @JsonProperty("connected_at")
                                    LocalDateTime connectedAt,
                                    ResponseKakaoProperties properties,
                                    @JsonProperty("kakao_account")
                                    ResponseKakaoAccount kakaoAccount
    ) {

}
