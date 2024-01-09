package com.example.sosikmemberservice.repository;

import com.example.sosikmemberservice.dto.request.RequestLogout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@Slf4j

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{
    private final RedisTemplate<String, String> redisTemplate;
    private final Long refreshTokenValidityInSeconds;

    public RefreshTokenRepositoryImpl(final RedisTemplate<String, String> redisTemplate,
                                      @Value("${jwt.refresh-token-validity-in-seconds}") final Long refreshTokenValidityInSeconds) {
        this.redisTemplate = redisTemplate;

        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    @Override
    public void save(String token,String memberIdentifier) {
        final long timeToLiveSeconds = refreshTokenValidityInSeconds / 1000;

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
    public void logout(final RequestLogout memberIdentifier) {
        redisTemplate.delete(memberIdentifier.email());
    }

    @Override
    public boolean existsByRefreshToken(String refreshToken) {
        if(redisTemplate.hasKey(refreshToken) != null){
            return false;
        }
        return true;
    }


}
