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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/v1")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/sign-up")
    public Result<Void> createMember(@RequestPart @Valid RequestSignup member,
                                     @RequestPart(required = false) MultipartFile profileImage) {
        memberService.createMember(member, profileImage);
        return Result.success();
    }

    @PostMapping("/sign-in")
    public Result<ResponseAuth> login(@RequestBody @Valid final RequestLogin requestLogin) {
        return Result.success(memberService.login(requestLogin));
    }

    @PostMapping("/sign-out")
    public Result<Void> logout(@RequestBody final String email) {
        memberService.deleteToken(email);
        return Result.success();
    }

    @PatchMapping
    public Result<Void> updateMember(@RequestHeader Long memberId,
                                     @RequestPart @Valid final RequestUpdateMember updateMember,
                                     @RequestPart(required = false) MultipartFile profileImage) {
        memberService.updateMember(memberId, updateMember, profileImage);
        return Result.success();
    }

    @PostMapping("/passwd")
    public Result<Void> sendEmail(@RequestBody final RequestFindPw requestFindPw) {
        Mail dto = mailService.createMailAndChangePassword(requestFindPw.email());
        mailService.mailSend(dto);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<GetMember> getMember(@RequestHeader Long memberId) {
        GetMember result = memberService.getMember(memberId);
        return Result.success(result);
    }

    @PostMapping("/weight")
    public Result<Void> createWeight(@RequestHeader Long memberId, @RequestBody RequestWeight requestWeight) {
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

    @GetMapping("/{memberId}")
    public GetMember getNickname(@PathVariable Long memberId) {
        GetMember result = memberService.getMember(memberId);
        return result;
    }

    @GetMapping("/validation/{email}")
    public boolean validationEmail(@PathVariable("email") String email) {
        Boolean checkResult = memberService.validation(email);
        return checkResult;
    }
    @GetMapping("/target-weight-data")
    public Result<ResponseGetManagementData> getTargetWeightData(@RequestHeader Long memberId){
        ResponseGetManagementData responseGetManagementData = memberService.getManagementData(memberId);
        return Result.success(responseGetManagementData);
    }

    @GetMapping("/weight-record-check")
    public boolean checkWeightRecord(@RequestHeader Long memberId){
        LocalDateTime start =  LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        return memberService.checkWeightTodayRecord(memberId,start,end);
    }

}
