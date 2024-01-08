package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.dto.request.*;
import com.example.sosikmemberservice.dto.response.GetMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.common.Result;
import com.example.sosikmemberservice.model.Mail;
import com.example.sosikmemberservice.model.Member;
import com.example.sosikmemberservice.service.MailService;
import com.example.sosikmemberservice.service.MemberServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@CrossOrigin
public class MemberController {
    private final MemberServiceImpl memberService;
    private final MailService mailService;
    @PostMapping("/v1/sign-up")
    public Result<Void> createMember(@RequestPart @Valid RequestSignup member,
                                     @RequestPart(required = false)  MultipartFile profileImage) {
        memberService.createMember(member,profileImage);
        return Result.success();
    }

    @PostMapping("/v1/sign-in")
    public Result<ResponseAuth> login(@RequestBody @Valid final RequestLogin requestLogin) {
        return Result.success(memberService.login(requestLogin));
    }
    @PostMapping("/v1/sign-out")
    public Result<Void> logout(@RequestBody final RequestLogout requestLogout) {
        memberService.logout(requestLogout);
        return Result.success();
    }
    @PatchMapping("/v1/")
    public Result<Void> updateMember(@RequestBody @Valid final RequestUpdateMember updateMember){
        memberService.updateMember(updateMember);
        return Result.success();
    }
    @PostMapping("/v1/passwd" )
    public Result<Void> sendEmail(@RequestBody final RequestFindPw requestFindPw){
        Mail dto = mailService.createMailAndChangePassword(requestFindPw.email());
        mailService.mailSend(dto);
        return Result.success();
    }
    @GetMapping("/v1/detail")
    public Result<GetMember> getMember(@AuthenticationPrincipal Member user) {
        GetMember result = memberService.getMember(user.memberId());
        return Result.success(result);
    }

}
