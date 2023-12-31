package com.example.sosikmemberservice.service;


import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.request.UpdateMember;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    String createMember(RequestMember memberDTO);
    String updateMember(UpdateMember updateMember);

    String login(RequestLogin login);
}
