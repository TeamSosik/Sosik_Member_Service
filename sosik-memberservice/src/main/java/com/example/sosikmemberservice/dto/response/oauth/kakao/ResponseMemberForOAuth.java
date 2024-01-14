package com.example.sosikmemberservice.dto.response.oauth.kakao;

import com.example.sosikmemberservice.model.entity.WeightEntity;
import lombok.Builder;

@Builder
public record ResponseMemberForOAuth(Long memberId) {
}
