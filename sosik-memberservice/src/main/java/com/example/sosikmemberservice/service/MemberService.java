package com.example.sosikmemberservice.service;


import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    RequestMember createMember(RequestMember memberDTO);

    ResponseAuth login(RequestLogin login);

    String logout(String email);


}
