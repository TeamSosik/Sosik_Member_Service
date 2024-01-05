package com.example.sosikmemberservice.service;


import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestLogout;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.request.RequestUpdate;
import com.example.sosikmemberservice.dto.response.GetMemberDTO;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.model.Member;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.entity.WeightEntity;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import com.example.sosikmemberservice.repository.RefreshTokenRepository;
import com.example.sosikmemberservice.repository.WeightRepository;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import com.example.sosikmemberservice.util.file.FileUtils;
import com.example.sosikmemberservice.util.file.ResultFileStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final WeightRepository weightRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final FileUtils filestore;

    public MemberEntity findMember(String email){
         return memberRepository.findByEmail(new Email(email))
                 .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    public RequestMember createMember(RequestMember memberDTO,MultipartFile profileImage){
        memberRepository.findByEmail(new Email(memberDTO.email())).ifPresent(it->{
            throw new ApplicationException(ErrorCode.DUPLICATED_USER_NAME);
        });
        ResultFileStore resultFileStore = null;
        try {
            resultFileStore = filestore.storeProfileFile(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MemberEntity member = MemberEntity.builder()
                .name(memberDTO.name())
                .password(encoder.encode(memberDTO.password()))
                .gender(memberDTO.gender())
                .email(memberDTO.email())
                .height(memberDTO.height())
                .activityLevel(memberDTO.activityLevel())
                .nickname(memberDTO.nickname())
                .profileImage(resultFileStore.folderPath() +"/"+resultFileStore.storeFileName())
                .birthday(memberDTO.birthday())
                .tdeeCalculation(memberDTO.tdeeCalculation())
                .build();

        memberRepository.save(member);
        return memberDTO;

    }


    @Transactional
    public String updateMember(RequestUpdate updateMember) {
        if(updateMember.memberId() == null || updateMember.currentWeight() == null || updateMember.targetWeight() == null
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
          String accessToken = jwtTokenUtils.createAccessToken(login.email(), "USER");
          String refreshToken = jwtTokenUtils.createRefreshToken(login.email(), "USER");
          saveToken(refreshToken,login.email());
          Member member = Member.fromEntity(entity);

        return  ResponseAuth.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .member(member)
                .build();
    }

    public String logout(RequestLogout email) {

        refreshTokenRepository.logout(email);
        return "로그아웃 완료";
    }
    private void saveToken(String refreshToken,String email) {
        refreshTokenRepository.save(refreshToken,email);
    }

    public boolean existsRefreshToken(String refreshToken) {
        return refreshTokenRepository.existsByRefreshToken(refreshToken);
    }

    public GetMemberDTO getMember(Long memberId) {

        MemberEntity member = memberRepository.findById(memberId).orElseThrow(() -> {
            return new ApplicationException(ErrorCode.USER_NOT_FOUND);
        });

        // 조회 완료
        // dto 생성하기
        GetMemberDTO getMemberDTO = GetMemberDTO.create(member);

        return getMemberDTO;
    }
}
