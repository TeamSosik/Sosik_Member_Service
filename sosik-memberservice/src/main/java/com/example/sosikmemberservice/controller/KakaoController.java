package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakao;
import com.example.sosikmemberservice.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class KakaoController {

    private final OAuthService oAuthService;

    @GetMapping("/kakao/token")
    @ResponseBody
    public ResponseKakao kakaoOauth(@RequestParam("code") String code) {
        return oAuthService.createMemberByOauth(code);
    }
}
