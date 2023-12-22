package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.dto.request.RequestMember;

public interface MemberService {

    String createMember(RequestMember memberDTO);
}
