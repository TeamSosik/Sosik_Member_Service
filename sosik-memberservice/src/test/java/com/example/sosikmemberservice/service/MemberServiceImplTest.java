package com.example.sosikmemberservice.service;


import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.model.entity.MemberEntity;

import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    private static final String PROFILE_IMAGE_URL ="image/url/ddd";
    private static final MemberEntity testMember1= testMember1();
    private static final MemberEntity testMember2= testMember2();
    private static final RequestMember testMemberDto= testMemberDto();

    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void beforEach(){
        System.out.println("테스트를 시작합니다.");
    }




    @DisplayName("회원가입시 가입에 성공한다.")
    @Test
    void givenTestMemberWhenCreateMemberThenSuccess(){

        RequestMember testMemberDto = testMemberDto();

        given(memberRepository.save(any())).willReturn(any());

        assertThat(memberService.createMember(testMemberDto)).isEqualTo("ok");



    }

    @DisplayName("회원가입시 가입에 실패한다 - 중복")
    @Test
    void givenTestMemberWhenCreateMemberThenThrowDUPLICATED_USER_NAME() {
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(testMember1()));

        assertThatThrownBy(()-> memberService.createMember(testMemberDto())).isInstanceOf(ApplicationException.class);

    }



    private static RequestMember testMemberDto(){
        return RequestMember.builder()
                .email("made_power1@naver.com")
                .password("12345678")
                .name("minu")
                .gender("male")
                .height(BigDecimal.valueOf(160))
                .activityLevel(3)
                .nickname("Minutaurus")
                .profileImage("c/trij/nrt")
                .birthday("2023/05/11")
                .build();
    }

    private static MemberEntity testMember1(){
        return MemberEntity.builder()
                .email("made_power1@naver.com")
                .password("12345678")
                .name("minu1")
                .gender("male")
                .height(BigDecimal.valueOf(160))
                .activityLevel(3)
                .nickname("Minutaurus1")
                .profileImage("c/trij/nrt")
                .birthday("2023/05/11")

                .build();
    }

    private static MemberEntity testMember2(){
        return MemberEntity.builder()
                .email("made_power2@naver.com")
                .password("12345678")
                .name("minu2")
                .gender("male")
                .height(BigDecimal.valueOf(160))
                .activityLevel(3)
                .nickname("Minutaurus2")
                .profileImage("c/trij/nrt")
                .birthday("2023/05/11")

                .build();
    }
}