package com.example.sosikmemberservice.dto.response.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ResponseKakaoToken(@JsonProperty("access_token")
                                 String accessToken,
                                 @JsonProperty("token_type")
                                 String tokenType,
                                 @JsonProperty("refresh_token")
                                 String refreshToken,
                                 @JsonProperty("expires_in")
                                 Integer expiresIn,
                                 @JsonProperty("scope")
                                 String scope,
                                 @JsonProperty("profile_image")
                                 String profileImage,
                                 @JsonProperty("profile_nickname")
                                 String profileNickname ,
                                 @JsonProperty("refresh_token_expires_in")
                                 Integer refreshTokenExpiresIn,
                                 @JsonProperty("accountEmail")
                                 String accountEmail
) {

}
