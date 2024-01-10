package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakao;

public interface OAuthService {
    ResponseKakao createMemberByOauth(String code);

}
