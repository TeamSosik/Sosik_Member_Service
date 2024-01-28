package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.common.Result;
import com.example.sosikmemberservice.dto.request.*;
import com.example.sosikmemberservice.dto.response.GetMember;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.dto.response.ResponseGetManagementData;
import com.example.sosikmemberservice.model.Mail;
import com.example.sosikmemberservice.service.MailService;
import com.example.sosikmemberservice.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
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
    public Result<Void> logout(@RequestBody final String email) {
        memberService.deleteToken(email);
        return Result.success();
    }

    @PatchMapping("/v1")
    public Result<Void> updateMember(@RequestHeader Long memberId,
                                     @RequestPart @Valid final RequestUpdateMember updateMember,
                                     @RequestPart(required = false) MultipartFile profileImage) {
        memberService.updateMember(memberId, updateMember, profileImage);
        return Result.success();
    }

    @PostMapping("/v1/passwd")
    public Result<Void> sendEmail(@RequestBody final RequestFindPw requestFindPw) {
        Mail dto = mailService.createMailAndChangePassword(requestFindPw.email());
        mailService.mailSend(dto);
        return Result.success();
    }

    @GetMapping("/v1/detail")
    public Result<GetMember> getMember(@RequestHeader Long memberId) {
        GetMember result = memberService.getMember(memberId);
        return Result.success(result);
    }

    @PostMapping("/v1/weight")
    public Result<Void> createWeight(@RequestHeader Long memberId, @RequestBody RequestWeight requestWeight) {
        memberService.createWeight(memberId, requestWeight);
        return Result.success();
    }

    //프로필사진 불러오기
    @GetMapping("/v1/images/{memberId}")
    public Resource showImage(@PathVariable Long memberId) throws MalformedURLException {
        GetMember result = memberService.getMember(memberId);
        String imageUrl = result.getProfileImage();
        return new UrlResource("file:" + imageUrl);
    }

    @GetMapping("/v1/{memberId}")
    public String getNickname(@PathVariable Long memberId) {
        GetMember result = memberService.getMember(memberId);
        String nickname = result.getNickname();
        return nickname;
    }

    @PostMapping("/v1/checkEmail/{email}")
    public boolean checkEmail(@PathVariable("email") String email) {
        Boolean checkResult = memberService.checkEmail(email);
        return checkResult;
    }
    @GetMapping("/v1/managementData")
    public Result<ResponseGetManagementData> getManagementData(@RequestHeader Long memberId){
        ResponseGetManagementData responseGetManagementData = memberService.getManagementData(memberId);
        return Result.success(responseGetManagementData);
    }

    @GetMapping("/v1/checkRecode")
    public boolean checkWeightRecode(@RequestHeader Long memberId){
        memberService.checkWeightTodayRecode(memberId);
        System.out.println(memberService.checkWeightTodayRecode(memberId));
        return memberService.checkWeightTodayRecode(memberId);
    }

}
