package com.example.mytodolist.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    //시크릿키를 랜덤으로 생성.
      /*
        @PostConstruct는 해당 객체가 빈 객체로 주입된 이후 수행되는 메소드를 가리킨다.
        JwtTokenProvider 클래스에는 @Component 어노테이션이 지정되어 있어 애플리케이션이 가동되면서 빈으로 자동주입된다.
        그때 @PostConstruct가 지정되어 있는 init() 메소드가 자동으로 실행된다.
       */

    private Key secretKey;

    private final long tokenValidMillisecond = 1000L * 60 * 60;


    //해당 객체가 빈 객체로 주입된 이후 수행되는 메서드를 가리킵니다.
    //@Component 어노테이션이 지정돼 있어 애플리케이션이 가동하면 자동으로 클래스가 빈등록이 되는데,
    //그 때 @PostConstruct 가 지정돼 있는 init() 메서드가 자동으로 실행됩니다.
    @PostConstruct
    protected void init(){
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        //secretKey를 Base64 형식으로 인코딩 합니다.
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    //Jwt 토큰의 내용에 값을 넣기 위해 Claims 객체를 생성합니다. setSubject() 메서드를 통해 sub 속성에 값을 추가하려면,
    //User의 uid 값을 사용합니다. 해당 토큰을 사용하는 사용자의 권한을 확인할 수 있는 role 값을 별개로 추가하였습니다.
    public String createToken(String userUid, List<String> roles){
        log.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userUid);
        claims.put("roles",roles);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(secretKey)
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    //필터에서 인증이 성공했을 때, SecurityContextHolder에 저장할 Authentication을 생성하는 역할.
    public Authentication getAuthentication(String token)
    {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",userDetails.getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    //토큰 클래스를 사용하기 위해 초기화를 위한 UserDetails가 필요하다.
    //Jwts.parser()를 통해 secretKey를 설정하고 클레임을 추출해서 토큰을 생성할 때 넣었던 sub값을 추출합니다.
    public String getUsername(String token){
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}",info);

        return info;
    }

    //이 메서드는 HttpServletRequest를 파라미터로 받아 헤더 값으로 전달된 "X-AUTH-TOKEN' 값을 가져와 리턴합니다.
    //클라이언트가 헤더를 통해 애플리케이션 서버로 JWT 토큰 값을 전달해야 정상적인 추출이 가능합니다.
    public String resolveToken(HttpServletRequest request){
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("JWT");
    }

    //토큰을 전달받아 클레임의 유효기간을 체크하고 boolean 타입의 값을 리턴하는 역할을 합니다.
    public boolean validateToken(String token){
        log.info("[validateToken] 토큰 유효 체크 시작");
        try{
            Jws<Claims> claims  = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            log.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }


}
