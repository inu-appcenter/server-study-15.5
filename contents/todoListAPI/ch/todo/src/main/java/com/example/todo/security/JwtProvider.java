package com.example.todo.security;


import com.example.todo.user.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final CustomUserDetailService customUserDetailService;

    @Value("${springboot.jwt.secret.key}")
    private String secret;

    private Key secretKey;

    private final long expirationTime = 1000L * 60 * 60;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String createToken(String account, List<Authority> roles) {
        Claims claims = Jwts.claims().setSubject(account);
        claims.put("roles", roles);

        Date now = new Date();

        String token = Jwts.builder()
                .setClaims (claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime () + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    /*
    23.12.23
    UserDetails의 getAuthorities() 메소드 내부적으로 User 엔티티의 roles 필드를 조회하려고 할 때,
    Lazily Fetch를 시도하게 된다. 이 때 최초 User 엔티티를 조회한 세션이 닫히면 영속성 컨텍스트가 클린-업되고
    더 이상 Proxy 객체의 State를 초기화할 수 없어 LazyInitializationException을 발생시킨다.
    이를 해결학 위해 getAuthentication() 메소드 범위로 Transaction을 확장시켜 영속성 컨텍스트가 유지되도록 만들었다.

    23.12.24
    UserRepository 인터페이스에 findByLoginId()를 대신하는 메소드 시그니처를 선언하고
    @Query를 통해 JPQL로 한 번에 가져오면 @Transactional을 통해 Lazily Fetch하는 것 보다 성능 상 이점이 있지않을까?
     */
    @Transactional
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getAccount(token));

        // 두번째 인자로 password를 넘기는 대신, 빈 문자열을 보낸다. (Token에 비밀번호를 넣는다는 것은.. 끔찍하다.)
        // 비밀번호는 인증과정에서만 사용되고 메모리에서 제거되어야 한다.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getAccount(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token) {
        try {
            // equalsIgnoreCase() : 두 문자열을 대소문자 구분없이 비교.
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("Bearer ")) {
                return false;
            }

            token = token.split(" ")[1].trim();
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
