package com.example.mytodolist.service;

import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.config.security.JwtTokenProvider;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.SignInResultDto;
import com.example.mytodolist.dto.SignUpRequestDto;
import com.example.mytodolist.dto.SignUpResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignService {

    public final UserRepository userRepository;
    public final JwtTokenProvider jwtTokenProvider;
    public final PasswordEncoder passwordEncoder;

    public void checkDuplicate(String uid)
    {
        if(userRepository.existsByUid(uid))
        {
            throw new DuplicateKeyException("이미 사용중인 아이디입니다.");
        }
    }
    public SignUpResultDto signUp(SignUpRequestDto signUpRequestDto, String role){

        log.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;
        if(role.equalsIgnoreCase("admin")){
           user = User.builder()
                   .uid(signUpRequestDto.getUid())
                   .name(signUpRequestDto.getName())
                   .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                   .email(signUpRequestDto.getEmail())
                   .roles(Collections.singletonList("ROLE_ADMIN"))
                   .build();
        }else{
            user = User.builder()
                    .uid(signUpRequestDto.getUid())
                    .name(signUpRequestDto.getName())
                    .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                    .email(signUpRequestDto.getEmail())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        
        log.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if(!savedUser.getName().isEmpty()){
            log.info("[getSignUpResult] 정상 처리 완료");
            signUpResultDto = SignUpResultDto.builder()
                    .id(savedUser.getId())
                    .uid(savedUser.getUid())
                    .name(savedUser.getName())
                    .email(savedUser.getEmail())
                    .level(savedUser.getLevel())
                    .build();
        }else{
            log.info("[getSignUpResult] 실패 처리 완료");
            signUpResultDto = SignUpResultDto.builder()
                    .id(savedUser.getId())
                    .uid(savedUser.getUid())
                    .name(savedUser.getName())
                    .email(savedUser.getEmail())
                    .level(savedUser.getLevel())
                    .build();
        }
        return signUpResultDto;
    }

    @Transactional
    public SignInResultDto signIn(String id, String password) throws RuntimeException{
        log.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        User user = userRepository.getByUid(id).orElseThrow(()->new NoSuchElementException("유저가 존재하지 않습니다."));
        log.info("[getSignInResult] Id : {}",id);

        log.info("[getSignInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new NullPointerException("로그인 실패. 아이디와 패스워드를 확인해주세요");
        }
        log.info("[getSignInResult] 패스워드 일치");

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.signInResultDtoBuilder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()),
                        user.getRoles()))
                .id(user.getId())
                .uid(user.getUid())
                .name(user.getName())
                .email(user.getEmail())
                .level(user.getLevel())
                .build();

        log.info("[getSignInReuslt] SignInResultDto 객체에 값 주입");

        return signInResultDto;
    }
}
