package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.dto.RequestMember;
import com.example.sosikmemberservice.entity.Member;
import com.example.sosikmemberservice.service.MemberService;
import com.example.sosikmemberservice.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public String createMember(@RequestBody RequestMember member) {
        log.info("=========================================================");
        memberService.createMember(member);
        log.info("=========================================================");
        return "ok";
    }
}
