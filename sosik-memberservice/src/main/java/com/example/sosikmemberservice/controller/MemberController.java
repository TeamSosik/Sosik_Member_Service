package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestLogout;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.dto.response.Result;
import com.example.sosikmemberservice.service.MemberService;
import com.example.sosikmemberservice.service.MemberServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;

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
    public Result<Void> logout(@RequestBody final RequestLogout request) throws ServletException {
        log.info(memberService.logout(request));
        return Result.success();
    }

//    @GetMapping(value = "/Test")
//    public Result<Void> headerCheck(HttpServletRequest servletRequest) {
//        return Result.success();
//    }


}
