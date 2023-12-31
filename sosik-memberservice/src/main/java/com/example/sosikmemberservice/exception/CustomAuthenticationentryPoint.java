package com.example.sosikmemberservice.exception;

import com.example.sosikmemberservice.dto.response.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationentryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(new ApplicationException(ErrorCode.INVALID_TOKEN_ERROR).getErrorCode().getStatus().value());
        response.getWriter().write(Result.error(new ApplicationException(ErrorCode.INVALID_TOKEN_ERROR).getErrorCode().name()).toStream());
    }
}
