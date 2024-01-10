package com.example.sosikmemberservice.dto.response.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseKakaoProfile(
         String nickname,
         @JsonProperty("profile_image_url")
         String profileImageUrl
) {
}
