package com.example.sosikmemberservice.config.interceptor;

import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private static final String BEARER ="Bearer ";
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof  final HandlerMethod handlerMethod)){
            return true;
        }
        if (handlerMethod.hasMethodAnnotation(Authenticated.class)){
            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            checkHeader(authorizationHeader);
            final String token = authorizationHeader.substring(BEARER.length());
            checkTokenCertify(token);
        }
        return true;


    }

    private void checkHeader(final String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            throw new ApplicationException(ErrorCode.INVALID_TOKEN_ERROR);
        }
    }

    private void checkTokenCertify(final String token) {
        if (!jwtTokenUtils.isValidToken(token)) {
            throw new ApplicationException(ErrorCode.INVALID_TOKEN_ERROR);
        }
    }
}
