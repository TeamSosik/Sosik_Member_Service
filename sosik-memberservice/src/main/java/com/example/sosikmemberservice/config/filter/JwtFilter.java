package com.example.sosikmemberservice.config.filter;

import com.example.sosikmemberservice.service.MemberServiceImpl;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenUtils utils;
    private final MemberServiceImpl memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs while getting header. header is null or invalid", request.getRequestURL());
            chain.doFilter(request, response);
            return;
        }
        try {
            final String token = header.split(" ")[1].trim();

            if (!utils.validateToken(token)) {
                chain.doFilter(request, response);
                return;
            }


            Authentication authentication = utils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: ", authentication.getName());
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: ", authentication);

        } catch (RuntimeException e) {
            log.error("Error occurs while validating {}", e.toString());
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/members/sign-up", "/members/login", "/members/reissue"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}

