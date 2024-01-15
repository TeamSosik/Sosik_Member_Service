package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.config.oauth.kakao.KakaoTokenJsonData;
import com.example.sosikmemberservice.config.oauth.kakao.KakaoUserInfo;
import com.example.sosikmemberservice.dto.request.RequestUpdateOAuthMember;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakao;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakaoToken;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakaoUserInfo;
import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseMemberForOAuth;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.model.entity.WeightEntity;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import com.example.sosikmemberservice.repository.RefreshTokenRepository;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import com.example.sosikmemberservice.util.file.FileUtils;
import com.example.sosikmemberservice.util.file.ResultFileStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class OAuthServiceImpl implements OAuthService {
    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final FileUtils filestore;
    private final KakaoUserInfo kakaoUserInfo;
    private final JwtTokenUtils jwtTokenUtils;
    @Value("${file.location}")
    private String uploadPath;

    public ResponseKakao createMemberByOauth(String code) {

        ResponseKakaoToken kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        ResponseKakaoUserInfo userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.accessToken());

        Optional<MemberEntity> existingMember = memberRepository.findByEmail(new Email(userInfo.kakaoAccount().email()));
        if (existingMember.isPresent()) {
            String refreshToken = jwtTokenUtils.createRefreshToken(existingMember.get().getEmail().getValue(),
                    "USER", existingMember.get().getMemberId());
            saveRefreshToken(refreshToken, userInfo);
            return ResponseKakao.builder()
                    .accessToken(jwtTokenUtils.createAccessToken(existingMember.get().getEmail().getValue(),
                            "USER",existingMember.get().getMemberId()))
                    .refreshToken(refreshToken)
                    .info(userInfo)
                    .member(new ResponseMemberForOAuth(existingMember.get().getMemberId()))
                    .isEnrolled(true)
                    .build();
        }

        MemberEntity member = MemberEntity.builder()
                .name(userInfo.kakaoAccount().profile().nickname())
                .password(encoder.encode(userInfo.kakaoAccount().email()))
                .email(userInfo.kakaoAccount().email())
                .nickname(userInfo.kakaoAccount().profile().nickname())
                .birthday("2000-01-01")
                .profileImage(uploadPath)
                .build();

        String refreshToken = jwtTokenUtils.createRefreshToken(userInfo.kakaoAccount().email(),
                "USER", member.getMemberId());

        saveRefreshToken(refreshToken, userInfo);
        memberRepository.save(member);
        Integer calculationWeek = BigDecimal.valueOf(0).subtract(BigDecimal.valueOf(0)).abs().intValue()*2;
        WeightEntity weightEntity = WeightEntity.create(BigDecimal.valueOf(0), BigDecimal.valueOf(0),calculationWeek);
        weightEntity.addMember(member);

        return ResponseKakao.builder()
                .accessToken(jwtTokenUtils.createAccessToken(member.getEmail().getValue(),"USER", member.getMemberId()))
                .refreshToken(refreshToken)
                .info(userInfo)
                .isEnrolled(false)
                .member(new ResponseMemberForOAuth(member.getMemberId()))
                .weightEntity(weightEntity)
                .build();
    }

    @Override
    public void updateOAuthMember(Long memberId, RequestUpdateOAuthMember updateMember, MultipartFile profileImage) {
        MemberEntity member = getMemberEntity(memberId);
        ResultFileStore resultFileStore = null;
        try {
            resultFileStore = filestore.storeProfileFile(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        member.updateProfileUrl(resultFileStore);
        member.updateOAuthMember(updateMember);
        WeightEntity weight = member.getWeight().get(member.getWeight().size()-1);
        weight.updateWeightForOAuth(updateMember);
    }

    private void saveRefreshToken(String refreshToken, ResponseKakaoUserInfo userInfo) {
        refreshTokenRepository.save(refreshToken,
                userInfo.kakaoAccount().email() + "OAuth");
    }

    private MemberEntity getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );
    }


}
