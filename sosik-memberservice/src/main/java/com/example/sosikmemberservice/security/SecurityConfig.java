package com.example.sosikmemberservice.security;

import com.example.sosikmemberservice.config.filter.JwtFilter;
import com.example.sosikmemberservice.exception.CustomAuthenticationentryPoint;
import com.example.sosikmemberservice.service.MemberService;
import com.example.sosikmemberservice.service.MemberServiceImpl;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final MemberServiceImpl memberService;
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspect) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new MvcRequestMatcher(introspect, "/**")).permitAll())
                .addFilterBefore(new JwtFilter(jwtTokenUtils,memberService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(new CustomAuthenticationentryPoint()))
                .build();
    }




}
