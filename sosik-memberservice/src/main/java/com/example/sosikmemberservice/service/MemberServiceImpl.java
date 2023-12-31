package com.example.sosikmemberservice.service;


import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestLogout;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.model.Member;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import com.example.sosikmemberservice.repository.RefreshTokenRepository;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    public Member loadUserByUserName(String email) {
        return memberRepository.findByEmail(new Email(email))
                .map(Member::fromEntity).orElseThrow(()->
                new ApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s 유저를 찾지 못했습니다!",email)));
    }

    public RequestMember createMember(RequestMember memberDTO) {
        memberRepository.findByEmail(new Email(memberDTO.email())).ifPresent(it->{
            throw new ApplicationException(ErrorCode.DUPLICATED_USER_NAME);
        });
        MemberEntity member = MemberEntity.builder()
                .name(memberDTO.name())
                .password(encoder.encode(memberDTO.password()))
                .gender(memberDTO.gender())
                .email(memberDTO.email())
                .height(memberDTO.height())
                .activityLevel(memberDTO.activityLevel())
                .nickname(memberDTO.nickname())
                .profileImage(memberDTO.profileImage())
                .birthday(memberDTO.birthday())
                .tdeeCalculation(memberDTO.tdeeCalculation())
                .build();

        memberRepository.save(member);
        return memberDTO;

    }

    public ResponseAuth login(RequestLogin login) {
        log.info("================= 로긴 서비스 단");
        log.info(login.email());
        MemberEntity entity = memberRepository.findByEmail(new Email(login.email()))
                .orElseThrow(IllegalArgumentException::new);
        if (!encoder.matches(login.password(), entity.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        String refreshToken = jwtTokenUtils.createRefreshToken(login.email(), Map.of());
        String accessToken = jwtTokenUtils.createAccessToken(login.email(), Map.of());
        saveToken(refreshToken ,entity);

        return new ResponseAuth(refreshToken,accessToken);

    }

    public String logout(RequestLogout email) {

        refreshTokenRepository.logout(email);
        return "로그아웃 완료";
    }
    private void saveToken(String refreshToken,MemberEntity member) {
        log.info("레디스 저장을 시작합니다!");
        refreshTokenRepository.save(refreshToken,member.getEmail().getValue() );
    }

}
