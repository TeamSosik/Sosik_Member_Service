package com.example.sosikmemberservice.model;

import com.example.sosikmemberservice.model.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getMemberRole().toString()));
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
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
    private  Long memberId;
    private String email;
    private String password;
    private MemberRole memberRole;
    private String birthday;
    private String profileImage;
    private String nickname;

    public static Member fromEntity(MemberEntity entity){
        return new Member(
                entity.getMemberId(),
                entity.getEmail().getValue(),
                entity.getPassword(),
                entity.getRole(),
                entity.getBirthday(),
                entity.getProfileImage().getValue(),
                entity.getNickname()
        );
    }
}
