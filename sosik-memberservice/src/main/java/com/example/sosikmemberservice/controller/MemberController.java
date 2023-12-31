package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.request.UpdateMember;
import com.example.sosikmemberservice.dto.response.Result;
import com.example.sosikmemberservice.service.MemberService;
import jakarta.validation.Valid;
import jakarta.ws.rs.PATCH;
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
    public Result<Void> createMember(@RequestBody @Valid RequestMember member) {
        log.info(member.password()+"  "+member.email());
        log.info("=========================================================");
        memberService.createMember(member);
        log.info("=========================================================");
        return Result.success();
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid RequestLogin request){
        log.info("================= 로긴 컨트롤러 단");
        log.info(request.email()+" "+request.password());
       return memberService.login(request);
    }
    @PatchMapping("")
    public Result<Void> updateMember(@RequestBody @Valid UpdateMember updateMember){
        memberService.updateMember(updateMember);
        return Result.success();
    }


}
