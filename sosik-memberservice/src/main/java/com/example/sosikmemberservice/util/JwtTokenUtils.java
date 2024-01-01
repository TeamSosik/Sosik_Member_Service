package com.example.sosikmemberservice.util;

import com.example.sosikmemberservice.dto.response.ResponseAuth;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenUtils {
        private final Key key;
        private final String secretKey;
        private final Long accessTokenValidityInSeconds;
        private final Long refreshTokenValidityInSeconds;

        public JwtTokenUtils(@Value("${jwt.secret-key}") final String secretKey,
                             @Value("#{T(Long).parseLong('${jwt.access-token-validity-in-seconds}')}") final Long accessTokenValidityInSeconds,
                             @Value("#{T(Long).parseLong('${jwt.refresh-token-validity-in-seconds}')}") final Long refreshTokenValidityInSeconds) {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            this.key = Keys.hmacShaKeyFor(keyBytes);
            this.secretKey = secretKey;
            this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
            this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
        }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
        public ResponseAuth generateToken(Authentication authentication) {
            // 권한 가져오기
            String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            long now = (new Date()).getTime();
            // Access Token 생성
            Date accessTokenExpiresIn = new Date(now + accessTokenValidityInSeconds);
            String accessToken = Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim("auth", authorities)
                    .setExpiration(accessTokenExpiresIn)
                    .signWith(createKey())
                    .compact();

            // Refresh Token 생성
            String refreshToken = Jwts.builder()
                    .setExpiration(new Date(now + refreshTokenValidityInSeconds))
                    .signWith(createKey())
                    .compact();

            return ResponseAuth.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        private SecretKey createKey() {
            final byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            return Keys.hmacShaKeyFor(secretKeyBytes);
        }



        private Claims parseClaimsJws(final String token) {
            String token1 = token;
            final SecretKey signingKey = createKey();
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)

                    .getBody();
        }
        private Claims extractClaims(String token){
            final SecretKey signingKey = createKey();
            return Jwts.parserBuilder().setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token).getBody();
        }
        public Authentication getAuthentication(String accessToken) {
            // 토큰 복호화
            Claims claims = parseClaimsJws(accessToken);

            if (claims.get("auth") == null) {
                throw new RuntimeException("권한 정보가 없는 토큰입니다.");
            }

            // 클레임에서 권한 정보 가져오기
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get("auth").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            // UserDetails 객체를 만들어서 Authentication 리턴
            UserDetails principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        }

        public boolean validateToken(String token) {
            try {
                parseClaimsJws(token);
            } catch (MalformedJwtException e) {
                log.info("Invalid JWT token");
                log.trace("Invalid JWT token trace = {}", e);
                throw new ApplicationException(ErrorCode.MALFORMED_TOKEN_ERROR);
            } catch (ExpiredJwtException e) {
                log.info("Expired JWT token");
                log.trace("Expired JWT token trace = {}", e);
                throw new ApplicationException(ErrorCode.EXPIRED_TOKEN_ERROR);
            } catch (UnsupportedJwtException e) {
                log.info("Unsupported JWT token");
                log.trace("Unsupported JWT token trace = {}", e);
                throw new ApplicationException(ErrorCode.UNSUPPORTED_TOKEN_ERROR);
            }
            return true;
        }

        public String findSubject(final String token) {
            Claims claims = parseClaimsJws(token);
            return claims.getSubject();
        }
}
