package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.common.Result;
import com.example.sosikmemberservice.dto.request.*;
import com.example.sosikmemberservice.dto.response.GetMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.model.Mail;
import com.example.sosikmemberservice.model.Member;
import com.example.sosikmemberservice.service.MailService;
import com.example.sosikmemberservice.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@CrossOrigin
public class MemberController {
    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/v1/sign-up")
    public Result<Void> createMember(@RequestPart @Valid RequestSignup member,
                                     @RequestPart(required = false) MultipartFile profileImage) {
        memberService.createMember(member, profileImage);
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
    public Result<Void> updateMember(@RequestBody @Valid final RequestUpdateMember updateMember,
                                     @RequestPart(required = false) MultipartFile profileImage,
                                     @AuthenticationPrincipal Member user) {
        memberService.updateMember(user.memberId(), updateMember, profileImage);
        return Result.success();
    }

    @PostMapping("/v1/passwd")
    public Result<Void> sendEmail(@RequestBody final RequestFindPw requestFindPw) {
        Mail dto = mailService.createMailAndChangePassword(requestFindPw.email());
        mailService.mailSend(dto);
        return Result.success();
    }

    @GetMapping("/v1/detail")
    public Result<GetMember> getMember(@AuthenticationPrincipal Member user) {
        GetMember result = memberService.getMember(user.memberId());
        return Result.success(result);
    }

    @PostMapping("/weight")
    public Result<Void> createWeight(@RequestHeader Long memberId, RequestWeight requestWeight) {
        memberService.createWeight(memberId, requestWeight);
        return Result.success();
    }

    //프로필사진 불러오기
    @GetMapping("/images/{memberId}")
    public Resource showImage(@PathVariable Long memberId) throws MalformedURLException {
        GetMember result = memberService.getMember(memberId);
        String imageUrl = result.getProfileImage();
        return new UrlResource("file:" + imageUrl);
    }

}
