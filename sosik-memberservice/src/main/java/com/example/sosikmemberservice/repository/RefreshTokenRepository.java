package com.example.sosikmemberservice.repository;

import com.example.sosikmemberservice.dto.request.RequestLogout;


public interface RefreshTokenRepository {
    void save(final String token,final String memberIdentifier);

    void deleteToken(final String email);

    boolean existsByRefreshToken(final String refreshToken);





}
