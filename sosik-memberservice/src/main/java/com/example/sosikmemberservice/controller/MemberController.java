package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.dto.request.*;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.dto.response.Result;
import com.example.sosikmemberservice.model.Mail;
import com.example.sosikmemberservice.service.MailService;
import com.example.sosikmemberservice.service.MemberServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;
    private final MailService mailService;

    @PostMapping("/sign-up")
    public Result<Void> createMember(@RequestBody @Valid final RequestMember member) {
        memberService.createMember(member);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<ResponseAuth> login(@RequestBody @Valid final RequestLogin request) {
        log.info(request.email()+" "+request.password());

        return Result.success(memberService.login(request));
    }

    @PostMapping(value = "/logout")
    public Result<Void> logout(@RequestBody final RequestLogout request) {
        log.info(memberService.logout(request));
        return Result.success();
    }

    @PatchMapping("/update")
    public Result<Void> updateMember(@RequestBody @Valid final RequestUpdate updateMember){
        memberService.updateMember(updateMember);
        return Result.success();
    }

    @PostMapping("/findpw" )
    public Result<Void> sendEmail(@RequestBody final RequestFindPw requestFindPw){
        Mail dto = mailService.createMailAndChangePassword(requestFindPw.email());
        mailService.mailSend(dto);

        return Result.success();
    }
}
