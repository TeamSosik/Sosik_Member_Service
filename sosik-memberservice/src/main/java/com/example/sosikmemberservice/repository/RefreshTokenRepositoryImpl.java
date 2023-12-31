package com.example.sosikmemberservice.repository;

import com.example.sosikmemberservice.dto.request.RequestLogout;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
@Repository
@Slf4j

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, String> redisBlackList;
    private final JwtTokenUtils jwtTokenUtils;
    private final Long refreshTokenValidityInSeconds;

    public RefreshTokenRepositoryImpl(final RedisTemplate<String, String> redisTemplate,
                                     JwtTokenUtils jwtTokenUtils, @Value("${jwt.refresh-token-validity-in-seconds}") final Long refreshTokenValidityInSeconds) {
        this.redisTemplate = redisTemplate;
        this.redisBlackList = redisTemplate;
        this.jwtTokenUtils = jwtTokenUtils;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    @Override
    public void save(String token,String memberIdentifier) {
        final long timeToLiveSeconds = refreshTokenValidityInSeconds / 1000;

        redisTemplate.opsForValue()
                .set(memberIdentifier,token,timeToLiveSeconds);

        log.info("저장을 완료했어요~");
    }

    @Override
    public Optional<String> findTokenByMemberIdentifier(String memberIdentifier) {
        final String email = redisTemplate.opsForValue().get(memberIdentifier);
        return Optional.of(email);
    }

    @Override
    public String logout(final RequestLogout memberIdentifier) {
        Optional<String> memberIdentifierByRefreshToken = findTokenByMemberIdentifier(memberIdentifier.email());
        String token = memberIdentifierByRefreshToken.orElseThrow(()-> new ApplicationException(ErrorCode.INVALID_TOKEN_ERROR));
        redisTemplate.delete(memberIdentifier.email());
        redisBlackList.opsForValue().set(token,"logout",5,TimeUnit.MINUTES);
        return "로그아웃 성공~";
    }


}
