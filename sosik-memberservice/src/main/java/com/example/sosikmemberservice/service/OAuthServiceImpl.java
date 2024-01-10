package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.config.oauth.kakao.KakaoTokenJsonData;
import com.example.sosikmemberservice.config.oauth.kakao.KakaoUserInfo;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakao;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakaoToken;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakaoUserInfo;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import com.example.sosikmemberservice.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuthServiceImpl implements OAuthService{
    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;
    public ResponseKakao createMemberByOauth(String code){

        ResponseKakaoToken kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        ResponseKakaoUserInfo userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.accessToken());

        Optional<MemberEntity> existingMember = memberRepository.findByEmail(new Email(userInfo.kakaoAccount().email()));
        if (existingMember.isPresent()) {
            saveRefreshToken(kakaoTokenResponse, userInfo);
            return ResponseKakao.builder()
                    .token(kakaoTokenResponse)
                    .info(userInfo)
                    .build();
        }
        MemberEntity member = MemberEntity.builder()
                .name(userInfo.kakaoAccount().profile().nickname())
                .password(encoder.encode(userInfo.kakaoAccount().email()))
                .email(userInfo.kakaoAccount().email())
                .nickname(userInfo.kakaoAccount().profile().nickname())
                .birthday("2000-01-01")
                .profileImage(userInfo.kakaoAccount().profile().profileImageUrl())
                .build();
        saveRefreshToken(kakaoTokenResponse, userInfo);
        memberRepository.save(member);
        return ResponseKakao.builder()
                .token(kakaoTokenResponse)
                .info(userInfo)
                .build();
     }


    private void saveRefreshToken(ResponseKakaoToken kakaoTokenResponse, ResponseKakaoUserInfo userInfo) {
        refreshTokenRepository.save(kakaoTokenResponse.refreshToken(),
                userInfo.kakaoAccount().email()+"OAuth");
    }


}
