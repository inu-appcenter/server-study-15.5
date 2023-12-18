package com.example.todolist.Security;

import com.example.todolist.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${JWT.SecretKey}")
    private String secretKey;
    private long validTokenTime = 30 * 60 * 1000L;
    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Long userId, List<GrantedAuthority> roleList) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("roleList", roleList);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // claim 저장
                .setIssuedAt(now) // 토큰 발행시간 저장
                .setExpiration(new Date(now.getTime() + validTokenTime)) // 토큰 유효시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 암호화 알고리즘과, secret 값
                .compact();
    }
    /*
        토큰에서 인증정보를 가져오는 메소드.
        토큰을 바탕으로 UsernamePasswordAuthenticationToken을 생성하고 SprintContextHolder에 저장해야한다.
        따라서 토큰에서 username을 추출한 뒤 이 값으로 MemberUserDetails객체를 생성한다.
        그 뒤 MemberUserDetails객체와 Authority를 통해 UsernamePasswordAuthenticationToken를 생성해 반환한다.
     */
    public Authentication getAuthentication(String token) {
        User user = (User) customUserDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(user.getUserId(),user.getPassword(),user.getAuthorities());
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        // request의 헤더에 ACT값을 읽어오기
        return request.getHeader("ACT");
    }

    public Long readUserIdByToken(HttpServletRequest request){
        return Long.valueOf(this.getUserId(request.getHeader("ACT")));
    }
}
