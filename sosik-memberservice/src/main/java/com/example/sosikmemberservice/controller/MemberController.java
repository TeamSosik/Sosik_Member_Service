package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.response.ResponseMember;
import com.example.sosikmemberservice.dto.response.Result;
import com.example.sosikmemberservice.service.MemberService;
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
    public Result<Void> createMember(@RequestBody RequestMember member) {
        log.info(member.password()+"  "+member.email());
        log.info("=========================================================");
        memberService.createMember(member);
        log.info("=========================================================");
        return Result.success();
    }
}
