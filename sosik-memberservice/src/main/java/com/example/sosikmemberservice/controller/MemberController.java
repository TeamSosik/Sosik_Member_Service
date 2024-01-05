package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.dto.request.*;
import com.example.sosikmemberservice.dto.response.GetMemberDTO;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.dto.response.Result;
import com.example.sosikmemberservice.model.Mail;
import com.example.sosikmemberservice.model.Member;
import com.example.sosikmemberservice.service.MailService;
import com.example.sosikmemberservice.dto.request.RequestLogin;
import com.example.sosikmemberservice.dto.request.RequestLogout;
import com.example.sosikmemberservice.dto.request.RequestMember;
import com.example.sosikmemberservice.dto.request.RequestUpdate;
import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.dto.response.Result;
import com.example.sosikmemberservice.service.MemberServiceImpl;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
@CrossOrigin
public class MemberController {

    private final MemberServiceImpl memberService;
    private final MailService mailService;

    @PostMapping("/sign-up")
    public Result<Void> createMember(@RequestPart @Valid RequestMember member,
                                     @RequestPart(required = false)  MultipartFile profileImage) {

        memberService.createMember(member,profileImage);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<ResponseAuth> login(@RequestBody @Valid final RequestLogin request) {
        log.info(request.email()+" "+request.password());
        return Result.success(memberService.login(request));
    }
    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody final RequestLogout request) {
        log.info(memberService.logout(request));
        return new ResponseEntity<>("정상적으로 로그아웃했습니다.",HttpStatus.OK);
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
    @PostMapping("/test" )
    public Result<Void> test(@RequestBody final RequestLogin RequestLogin, @AuthenticationPrincipal Member user){
        log.info(user.getNickname());
        return Result.success();
    }

    @GetMapping("/v1/detail")
    public Result<GetMemberDTO> getMember(@AuthenticationPrincipal Member user) {

        GetMemberDTO result = memberService.getMember(user.getMemberId());
        return Result.success(result);
    }

}
