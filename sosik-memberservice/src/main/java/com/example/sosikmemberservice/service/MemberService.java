package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.dto.request.*;
import com.example.sosikmemberservice.dto.response.GetMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.dto.response.ResponseGetManagementData;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public interface MemberService {

    MemberEntity findMember(String email);
    void createMember(RequestSignup memberDTO, MultipartFile profileImage);
    void updateMember(Long memberId,RequestUpdateMember updateMember,MultipartFile profileImage);
    ResponseAuth login(RequestLogin login);
    void logout(RequestLogout email);
    void saveToken(String refreshToken,String email);
    boolean existsRefreshToken(String refreshToken);
    GetMember getMember(Long memberId);
    void createWeight(Long memberId, RequestWeight weightDTO);
    boolean validation(String email);
    ResponseGetManagementData getManagementData(Long memberId);
    boolean checkWeightTodayRecord(Long memberId, LocalDateTime start, LocalDateTime end);

}
