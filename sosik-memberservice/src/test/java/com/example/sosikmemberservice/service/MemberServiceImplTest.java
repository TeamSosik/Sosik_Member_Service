package com.example.sosikmemberservice.service;


import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.request.RequestUpdate;
import com.example.sosikmemberservice.dto.request.RequestWeight;
import com.example.sosikmemberservice.dto.response.GetMember;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.entity.MemberEntity;

import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.model.entity.WeightEntity;
import com.example.sosikmemberservice.repository.MemberRepository;
import com.example.sosikmemberservice.repository.WeightRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    private static final String PROFILE_IMAGE_URL = "image/url/ddd";
    private static final MemberEntity testMember1 = testMember1();
    private static final MemberEntity testMember2 = testMember2();
    private static final RequestMember testMemberDto = testMemberDto();

    private static final WeightEntity testWeightDto = testWeight();
    private static final RequestWeight testWeightDTO = testWeightDTO();
//    private static final WeightEntity testWeightDto = testWeight();

    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private WeightRepository weightRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void beforEach() {
        System.out.println("테스트를 시작합니다.");
    }


    @DisplayName("회원가입시 가입에 성공한다.")
    @Test
    void givenTestMemberWhenCreateMemberThenSuccess() {

        RequestMember testMemberDto = testMemberDto();

        given(memberRepository.save(any())).willReturn(any());

//        assertThat(memberService.createMember(testMemberDto)).isEqualTo("ok");


    }

    @DisplayName("회원가입시 가입에 실패한다 - 중복")
    @Test
    void givenTestMemberWhenCreateMemberThenThrowDUPLICATED_USER_NAME() {
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(testMember1()));

//        assertThatThrownBy(()-> memberService.createMember(testMemberDto())).isInstanceOf(ApplicationException.class);

    }


//    @DisplayName("회원정보 수정에 성공한다.")
//    @Test
//    void givenTestMemberWhenUpdateMemberThenUpdateSuccess(){
//        RequestUpdate testUpdateSuccessDto = updateTest();
//        given(memberRepository.findById(testUpdateSuccessDto.memberId())).willReturn(Optional.of(testMember1));
//        given(weightRepository.findById(testUpdateSuccessDto.weightId())).willReturn(Optional.of(testWeightDto));
//        testMember1.updateMember(testUpdateSuccessDto);
//        testWeightDto.updateWeight(testUpdateSuccessDto);
//        assertThat(memberService.updateMember(testUpdateSuccessDto)).isEqualTo("ok");
//
//    }
//    @DisplayName("회원정보 수정에 실패한다. - 객체에 null 값이 있음")
//    @Test
//    void givenTestMemberWhenUpdateMemberThrowUPDATEMEMBER_EMPTY_COLUMN_ERROR(){
//        RequestUpdate updateTestMember = updateTest2(); //null값이 있는 객체
//        assertThatThrownBy(()-> memberService.updateMember(updateTestMember)).isInstanceOf(IllegalArgumentException.class);
//    }
//
//
//    private static WeightEntity testWeight(){
//        return WeightEntity.builder()
//                .id(1L)
//                .currentWeight(BigDecimal.valueOf(160))
//                .targetWeight(BigDecimal.valueOf(160))
//                .member(testMember1())
//                .build();
//    }
//    private static RequestUpdate updateTest(){
//        return RequestUpdate.builder()
//                .memberId(1L)
//                .weightId(1L)
//                .currentWeight(BigDecimal.valueOf(200))
//                .targetWeight(BigDecimal.valueOf(150))
//                .height(BigDecimal.valueOf(175))
//                .profileImage("c/trij/nrt")
//                .nickname("Minutaurus")
//                .activityLevel(1)
//                .build();
//    }
//    private static RequestUpdate updateTest2(){ //현재 체중값이 null
//        return RequestUpdate.builder()
//                .memberId(1L)
//                .weightId(1L)
//                .targetWeight(BigDecimal.valueOf(150))
//                .height(BigDecimal.valueOf(175))
//                .profileImage("c/trij/nrt")
//                .nickname("Minutaurus")
//                .activityLevel(1)
//                .build();
//    }
//

    private static RequestMember testMemberDto() {
        return RequestMember.builder()
                .email("made_power1@naver.com")
                .password("12345678")
                .name("minu")
                .gender("male")
                .height(BigDecimal.valueOf(160))
                .activityLevel(3)
                .nickname("Minutaurus")
//                .profileImage("c/trij/nrt")
                .birthday("2023/05/11")
                .build();
    }

    private static MemberEntity testMember1() {
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

    private static MemberEntity testMember2() {
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

    @Test
    void 회원정보조회실패_존재하지않는회원아이디() {

        // given
        Long memberId = 1L;

        Mockito.doReturn(Optional.empty())
                .when(memberRepository)
                .findById(memberId);

        // when
        ApplicationException result = assertThrows(ApplicationException.class, () -> {
            memberService.getMember(memberId);
        });

        // then
        assertThat(result.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);

    }

    @Test
    void 회원조회성공() {

        // given
        Long memberId = 1L;
        MemberEntity member = getTestMember3WithId(memberId);

        // 최근 기록
        WeightEntity weight1 = WeightEntity.builder()
                .id(memberId)
                .currentWeight(new BigDecimal(60.1))
                .targetWeight(new BigDecimal(50.5))
                .build();
        // 과거 기록
        WeightEntity weight2 = WeightEntity.builder()
                .id(2L)
                .currentWeight(new BigDecimal(70.1))
                .targetWeight(new BigDecimal(60.1))
                .build();

        weight1.addMember(member);
        weight2.addMember(member);

        // 조회
        Mockito.doReturn(Optional.of(member))
                .when(memberRepository)
                .findById(memberId);

        // when
        // dto로 변환하기
        GetMember result = memberService.getMember(memberId);

        // then
        assertThat(result.getMemberId()).isEqualTo(memberId);
        assertThat(result.getWeightList().size()).isEqualTo(2);

    }

    private static MemberEntity getTestMember3WithId(Long memberId) {
        return MemberEntity.builder()
                .memberId(memberId)
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

    @DisplayName("체중 기록 시 정상적으로 작동된다")
    @Test
    void givenTestWeightWhenCreateIntakeThenSuccess() {
        RequestWeight testWeightDTO = testWeightDTO();
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(testMember1));
        assertThat(memberService.createWeight(1L, testWeightDTO)).isEqualTo(testWeightDTO);
    }

    private static RequestWeight testWeightDTO() {
        return RequestWeight.builder()
                .member(testMember1())
                .currentWeight(BigDecimal.valueOf(80))
                .targetWeight(BigDecimal.valueOf(70))
                .build();
    }
}