package com.example.sosikmemberservice.config.filter;

import com.example.sosikmemberservice.service.MemberService;
import com.example.sosikmemberservice.service.MemberServiceImpl;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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

            if (utils.isExpired(token)) {
                log.error("Key is expired");
                chain.doFilter(request, response);
                return;
            }

            String userName = utils.getUserName(token);
            UserDetails userDetails = memberService.loadUserByUserName(userName);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException e) {
            log.error("Error occurs while validating {}", e.toString());
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}

