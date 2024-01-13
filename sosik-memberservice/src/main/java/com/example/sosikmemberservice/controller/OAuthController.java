package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.common.Result;
import com.example.sosikmemberservice.dto.request.RequestUpdateOAuthMember;
import com.example.sosikmemberservice.dto.response.GetMember;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakao;
import com.example.sosikmemberservice.service.MemberService;
import com.example.sosikmemberservice.service.OAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;
    private final MemberService memberService;

    @GetMapping("/kakao/token")
    public ResponseKakao kakaoOauth(@RequestParam("code") String code) {
        return oAuthService.createMemberByOauth(code);
    }
    @PatchMapping("/v1")
    public Result<GetMember> updateMember(@RequestHeader String memberId2,
                                          @RequestPart @Valid final RequestUpdateOAuthMember enrollMemberOauth,
                                          @RequestPart(required = false) MultipartFile profileImage) {
        oAuthService.updateOAuthMember(Long.parseLong(memberId2), enrollMemberOauth, profileImage);
        GetMember member = memberService.getMember(Long.parseLong(memberId2));
        return Result.success(member);
    }
}
