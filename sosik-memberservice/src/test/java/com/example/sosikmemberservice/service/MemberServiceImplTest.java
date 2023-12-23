package com.example.sosikmemberservice.service;


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


import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    MemberServiceImpl service;
    @Mock
    MemberRepository repository;

    @BeforeEach
    void beforEach(){
        System.out.println("테스트를 시작합니다.");
        MemberEntity member = testMember();
        MemberEntity save = repository.save(member);
        System.out.println(save);
        System.out.println(repository.count());
    }




    @DisplayName("회원가입시 가입에 성공한다.")
    @Test
    void givenTestMemberWhenCreateMemberThenSuccess(){

        RequestMember requestMember = testMemberDto();

        // then
        String ok = service.createMember(requestMember);

        assertThat(ok).isEqualTo("");

    }

    @DisplayName("회원가입시 가입에 실패한다 - 중복")
    @Test
    void givenTestMemberWhenCreateMemberThenThrowDUPLICATED_USER_NAME() {
        // given
        RequestMember requestMember1 = testMemberDto();
        service.createMember(requestMember1);

        // when
        assertThrows(ApplicationException.class, ()-> service.createMember(requestMember1));

        // then


    }



    private static RequestMember testMemberDto(){
        return RequestMember.builder()
                .email("made_power@naver.com")
                .password("1234")
                .name("minu")
                .gender("male")
                .height(BigDecimal.valueOf(160))
                .activityLevel(3)
                .nickname("Minutaurus")
                .profileImage("c/trij/nrt")
                .birthday("2023/05/11")
                .build();
    }

    private static MemberEntity testMember(){
        return MemberEntity.builder()
//                .email("made_power@naver.com")
                .password("1234")
                .name("minu")
                .gender("male")
                .height(BigDecimal.valueOf(160))
                .activityLevel(3)
                .nickname("Minutaurus")
                .profileImage("c/trij/nrt")
                .birthday("2023/05/11")
                .build();
    }
}