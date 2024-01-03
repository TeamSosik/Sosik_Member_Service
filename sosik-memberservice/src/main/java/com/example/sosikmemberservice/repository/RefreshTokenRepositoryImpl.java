package com.example.sosikmemberservice.repository;

import com.example.sosikmemberservice.dto.request.RequestLogout;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
@Repository
@Slf4j

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenUtils jwtTokenUtils;
    private final Long refreshTokenValidityInSeconds;

    public RefreshTokenRepositoryImpl(final RedisTemplate<String, String> redisTemplate,
                                     JwtTokenUtils jwtTokenUtils, @Value("${jwt.refresh-token-validity-in-seconds}") final Long refreshTokenValidityInSeconds) {
        this.redisTemplate = redisTemplate;
        this.jwtTokenUtils = jwtTokenUtils;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    @Override
    public void save(String token,String memberIdentifier) {
        final long timeToLiveSeconds = refreshTokenValidityInSeconds / 1000;
        log.info(token);

        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations
                .set(memberIdentifier,token,timeToLiveSeconds);
    }

    // TO-DO : 토큰 값이 이상하게 나온다.
    @Override
    public Optional<String> findTokenByMemberIdentifier(String memberIdentifier) {
        final String email = redisTemplate.opsForValue().get(memberIdentifier);
        return Optional.of(email);
    }

    @Override
    public String logout(final RequestLogout memberIdentifier) {
        redisTemplate.delete(memberIdentifier.email());
        return "로그아웃 성공~";
    }

    @Override
    public boolean existsByRefreshToken(String refreshToken) {
        if(redisTemplate.hasKey(refreshToken) != null){
            return false;
        }
        return true;
    }


}
