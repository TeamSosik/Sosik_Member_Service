package com.example.sosikmemberservice.security;

import com.example.sosikmemberservice.model.Member;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {

        MemberEntity fromMember = memberRepository.findByEmail(new Email(username)).orElseThrow(IllegalArgumentException::new);
        Member member = Member.fromEntity(fromMember);
        return createUserDetails(member);
    }

    private UserDetails createUserDetails(Member member){
        String password = member.getPassword();
        return User.builder()
                .username(member.email())
                .roles(member.memberRole().name())
                .password(password)
                .build();
    }
}
