package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.dto.request.*;
import com.example.sosikmemberservice.dto.response.GetMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    RequestSignup createMember(RequestSignup memberDTO, MultipartFile profileImage);
    void updateMember(Long memberId,RequestUpdateMember updateMember,MultipartFile profileImage);
    ResponseAuth login(RequestLogin login);
    void logout(RequestLogout email);
    boolean existsRefreshToken(String refreshToken);
    GetMember getMember(Long memberId);
    RequestWeight createWeight(Long memberId, RequestWeight weightDTO);
}
