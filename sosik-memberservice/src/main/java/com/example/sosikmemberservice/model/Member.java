package com.example.sosikmemberservice.model;

import com.example.sosikmemberservice.model.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public record Member(
        Long memberId,
        String email,
        String password,
        String gender,
        MemberRole memberRole,
        String birthday,
        String profileImage,
        String nickname
) implements UserDetails{

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.memberRole.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static Member fromEntity(MemberEntity entity) {
        return new Member(
                entity.getMemberId(),
                entity.getEmail().getValue(),
                entity.getPassword(),
                entity.getGender(),
                entity.getRole(),
                entity.getBirthday(),
                entity.getProfileImage().getValue(),
                entity.getNickname()
        );
    }
}


