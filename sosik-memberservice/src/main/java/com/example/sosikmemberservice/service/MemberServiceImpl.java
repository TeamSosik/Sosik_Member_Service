package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.dto.RequestMember;
import com.example.sosikmemberservice.entity.Member;
import com.example.sosikmemberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String createMember(RequestMember memberDTO) {
        Member member = Member.builder()
                .name(memberDTO.getName())
                .password(memberDTO.getPassword())
                .gender(memberDTO.getGender())
                .email(memberDTO.getEmail())
                .activityLevel(memberDTO.getActivityLevel())
                .height(memberDTO.getHeight())
                .nickname(memberDTO.getNickname())
                .profileImage(memberDTO.getProfileImage())
                .birthday(memberDTO.getBirthday())
                .build();
        memberRepository.save(member);
        return "ok";
    }
}
