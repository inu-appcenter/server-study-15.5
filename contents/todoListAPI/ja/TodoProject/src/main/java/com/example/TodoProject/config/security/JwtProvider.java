package com.example.TodoProject.config.security;

import com.example.TodoProject.entity.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final UserDetailsService userDetailsService; // Spring Security 에서 제공하는 서비스 레이어

    private Key secretKey;
    private final long tokenValidMillisecond = 1000L * 60 * 60; // 1시간 토큰 유효


    @PostConstruct
    protected void init() {
        log.info("[init] 시크릿키 초기화 시작");
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        log.info("[init] 시크릿키 초기화 성공");
    }

    public String createToken(String clientId,Long clientNum, List<String> roles) {
        log.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(clientId);
        claims.put("roles", roles);
        claims.put("clientNum", clientNum);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
                userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }

    public Long getClientNum(String token){
        log.info("[getClientNum] 토큰 기반 회원 구별 정보 추출");
        Long info = Long.valueOf(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("clientNum").toString());
        log.info("[getClientNum] 토큰 기반 회원 구별 정보 추출 완료, clientNum : {}", info);
        return info;
    }


    public String resolveToken(HttpServletRequest request) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            log.info("[validateToken] 토큰 유효 체크 성공");
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.info("[validateToken] 토큰 유효 체크 중 오류 발생");
            return false;
        }
    }
}