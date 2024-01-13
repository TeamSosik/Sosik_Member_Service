package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.dto.request.RequestUpdateOAuthMember;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakao;
import org.springframework.web.multipart.MultipartFile;

public interface OAuthService {
    ResponseKakao createMemberByOauth(String code);
    void updateOAuthMember(Long memberId, RequestUpdateOAuthMember updateMember, MultipartFile profileImage);

}
