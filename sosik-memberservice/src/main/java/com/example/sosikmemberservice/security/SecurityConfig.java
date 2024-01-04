package com.example.sosikmemberservice.security;

import com.example.sosikmemberservice.config.filter.JwtFilter;
import com.example.sosikmemberservice.exception.CustomAuthenticationentryPoint;
import com.example.sosikmemberservice.repository.RefreshTokenRepository;
import com.example.sosikmemberservice.service.MemberServiceImpl;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenRepository repository;
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspect) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new MvcRequestMatcher(introspect, "/**")).permitAll()
                )
                .addFilterBefore(new JwtFilter(jwtTokenUtils,repository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter(), CorsFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(new CustomAuthenticationentryPoint()))
                .build();
    }


    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Cors Config를 만들기
        CorsConfiguration corsConfig = new CorsConfiguration();
        // 옵션을 설정하기
        corsConfig.setAllowCredentials(true);// json 서버 응답을 자바스크립트에서 처리할 수 있도록 허용

        corsConfig.addAllowedOriginPattern("http://localhost:3000");

        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.addAllowedMethod(HttpMethod.PATCH);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);

//        corsConfig.addAllowedHeader("Authorization");
        corsConfig.addAllowedHeader(HttpHeaders.AUTHORIZATION);
        corsConfig.addAllowedHeader("*");

        // 어디에 CorsFilter를 적용할래?
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(source);
    }




}
