package com.example.sosikmemberservice.util;

import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.Member;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component


public class JwtTokenUtils {
        private final Key key;
        private final String secretKey;
        private final Long accessTokenValidityInSeconds;
        private final Long refreshTokenValidityInSeconds;
        private final MemberRepository memberRepository;

        public JwtTokenUtils(@Value("${jwt.secret-key}") final String secretKey,
                             @Value("#{T(Long).parseLong('${jwt.access-token-validity-in-seconds}')}") final Long accessTokenValidityInSeconds,
                             @Value("#{T(Long).parseLong('${jwt.refresh-token-validity-in-seconds}')}") final Long refreshTokenValidityInSeconds,
                             final MemberRepository memberRepository) {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            this.key = Keys.hmacShaKeyFor(keyBytes);
            this.secretKey = secretKey;
            this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
            this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
            this.memberRepository = memberRepository;
        }

        // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
        // Access Token 생성.
        public String createAccessToken(String email, String role){
            return this.createToken(email, role, accessTokenValidityInSeconds);
        }
        // Refresh Token 생성.
        public String createRefreshToken(String email, String role) {
            return this.createToken(email, role, refreshTokenValidityInSeconds);
        }

        // Create token
        public String createToken(String email, String roles, long tokenValid) {
            Claims claims = Jwts.claims().setSubject(email); // claims 생성 및 payload 설정
            claims.put("auth", roles); // 권한 설정, key/ value 쌍으로 저장

            Date date = new Date();
            return Jwts.builder()
                    .setClaims(claims) // 발행 유저 정보 저장
                    .setIssuedAt(date) // 발행 시간 저장
                    .setExpiration(new Date(date.getTime() + tokenValid)) // 토큰 유효 시간 저장
                    .signWith(SignatureAlgorithm.HS256, createKey()) // 해싱 알고리즘 및 키 설정
                    .compact(); // 생성
        }

        private SecretKey createKey() {
            final byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            return Keys.hmacShaKeyFor(secretKeyBytes);
        }

        private Claims parseClaimsJws(final String token) {
            final SecretKey signingKey = createKey();
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        public String getUserEmail(String token) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
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
            MemberEntity entity = memberRepository.findByEmail(new Email(claims.getSubject())).get();
            UserDetails principal = Member.fromEntity(entity);
            return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        }

        // 어세스 토큰 헤더 설정
        public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
            response.setHeader("authorization", "bearer "+ accessToken);
        }

        // 리프레시 토큰 헤더 설정
        public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
            response.setHeader("refreshToken", "bearer "+ refreshToken);
        }

        public String resolveAccessToken(HttpServletRequest request) {
            if(request.getHeader("authorization") != null )
                return request.getHeader("authorization").substring(7);
            return null;
        }
        // Request의 Header에서 RefreshToken 값을 가져옵니다. "authorization" : "token'
        public String resolveRefreshToken(HttpServletRequest request) {
            if(request.getHeader("refreshToken") != null )
                return request.getHeader("refreshToken").substring(7);
            return null;
        }
        public boolean validateToken(String token) {
            try {
                parseClaimsJws(token);
            } catch (MalformedJwtException e) {
                throw new ApplicationException(ErrorCode.MALFORMED_TOKEN_ERROR);
            } catch (ExpiredJwtException e) {
                throw new ApplicationException(ErrorCode.EXPIRED_TOKEN_ERROR);
            } catch (UnsupportedJwtException e) {
                throw new ApplicationException(ErrorCode.UNSUPPORTED_TOKEN_ERROR);
            }
            return true;
        }



}
