package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String createMember(RequestMember memberDTO) {
        memberRepository.findByEmail(new Email(memberDTO.email())).ifPresent(it->{
            throw new ApplicationException(ErrorCode.DUPLICATED_USER_NAME);
        });
        MemberEntity member = MemberEntity.builder()
                .name(memberDTO.name())
                .password(memberDTO.password())
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
}
