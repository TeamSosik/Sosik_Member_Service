package com.example.sosikmemberservice.service;


import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestLogout;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.model.Member;
import com.example.sosikmemberservice.model.MemberRole;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import com.example.sosikmemberservice.repository.RefreshTokenRepository;
import com.example.sosikmemberservice.repository.WeightRepository;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    public MemberEntity findMember(String email){
         return memberRepository.findByEmail(new Email(email))
                .orElseThrow(IllegalArgumentException::new);

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


    @Transactional
    public String updateMember(UpdateMember updateMember) {
        if(updateMember.memberId() == null || updateMember.currentWeight() == null || updateMember.goalWeight() == null
                || updateMember.weightId() == null || updateMember.activityLevel() == null
                || updateMember.height() == null || updateMember.nickname() == null || updateMember.profileImage() == null )
        {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.UPDATEMEMBER_EMPTY_COLUMN_ERROR));
        };
        MemberEntity member = memberRepository.findById(updateMember.memberId()).get();
        WeightEntity weight = weightRepository.findById(updateMember.weightId()).get();
        member.updateMember(updateMember);
        weight.updateWeight(updateMember);
        return "ok";
    }

      public ResponseAuth login(RequestLogin login) {
        log.info("================= 로긴 서비스 단");
        log.info(login.email());
        MemberEntity entity = memberRepository.findByEmail(new Email(login.email()))
                .orElseThrow(IllegalArgumentException::new);
        if (!encoder.matches(login.password(), entity.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        ResponseAuth responseAuth = jwtTokenUtils.generateToken(authentication);
        saveToken(responseAuth.refreshToken() ,entity);

        return responseAuth;

    }

    public String logout(RequestLogout email) {

        refreshTokenRepository.logout(email);
        return "로그아웃 완료";
    }
    private void saveToken(String refreshToken,MemberEntity member) {
        refreshTokenRepository.save(refreshToken,member.getEmail().getValue() );
    }

}
