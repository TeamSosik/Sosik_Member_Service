package com.example.sosikmemberservice.service;


import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import com.example.sosikmemberservice.repository.RefreshTokenRepository;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MemberEntity> entity = memberRepository.findByEmail(new Email(email));
        entity.ifPresent(it->{
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        });
        String id = entity.get().getEmail().getValue();
        String password = entity.get().getPassword();
        return new User(
                id,
                password,
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }

    @Override
    public String createMember(RequestMember memberDTO) {
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
        return "ok";

    }
    @Override
    public String login(RequestLogin login) {
        log.info("================= 로긴 서비스 단");
        log.info(login.email());
        MemberEntity entity = memberRepository.findByEmail(new Email(login.email()))
                .orElseThrow(IllegalArgumentException::new);
        if (!encoder.matches(login.password(), entity.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        String refreshToken = jwtTokenUtils.createRefreshToken(login.email(), Map.of());
//        saveRefreshToken(refreshToken, entity);
        return jwtTokenUtils.createAccessToken(login.email(), Map.of());
    }


    private void saveRefreshToken(String refreshToken, MemberEntity member) {
        refreshTokenRepository.save(refreshToken, member.getEmail().getValue());
    }



}
