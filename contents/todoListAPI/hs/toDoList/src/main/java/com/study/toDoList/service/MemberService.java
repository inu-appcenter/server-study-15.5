package com.study.toDoList.service;

import antlr.Token;
import com.study.toDoList.config.TokenProvider;
import com.study.toDoList.domain.Member;
import com.study.toDoList.dto.MemberListResponseDto;
import com.study.toDoList.dto.MemberResponseDto;
import com.study.toDoList.dto.MemberSaveDto;
import com.study.toDoList.dto.MemberUpdateDto;
import com.study.toDoList.exception.ex.MyDuplicateException;
import com.study.toDoList.exception.ex.MyErrorCode;
import com.study.toDoList.exception.ex.MyNotFoundException;
import com.study.toDoList.repositoy.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public Long save(MemberSaveDto memberSaveDto){
        String encodedPassword = passwordEncoder.encode(memberSaveDto.getPassword());/*받은 비밀번호 암호화*/
        if(memberRepository.existsByEmail(memberSaveDto.getEmail())){
            throw new MyDuplicateException(MyErrorCode.USER_DUPLICATE_EMAIL);
        }
        if(memberRepository.existsByNickname(memberSaveDto.getNickname())){
            throw new MyDuplicateException(MyErrorCode.USER_DUPLICATE_NICKNAME);
        }
        if(memberSaveDto.getEmail().equals("hen715@naver.com")){
            Member member= Member.builder().email(memberSaveDto.getEmail()).password(encodedPassword).nickname(memberSaveDto.getNickname()).roles(Collections.singletonList("ROLE_ADMIN")).build();
            return memberRepository.save(member).getId();

        }
        else {
            Member member = Member.builder().email(memberSaveDto.getEmail()).password(encodedPassword).nickname(memberSaveDto.getNickname()).roles(Collections.singletonList("ROLE_USER")).build();
            return memberRepository.save(member).getId();

        }
    }

    @Transactional
    public Long update(Long id, MemberUpdateDto memberUpdateDto){
        Member member = memberRepository.findById(id).orElseThrow(()->new MyNotFoundException(MyErrorCode.USER_NOT_FOUND));
        if(memberRepository.existsByNickname(memberUpdateDto.getNickname())){
            throw new MyDuplicateException(MyErrorCode.USER_DUPLICATE_NICKNAME);
        }
        String encodedPassword = passwordEncoder.encode(memberUpdateDto.getPassword());
        member.update(encodedPassword,memberUpdateDto.getNickname());
        return id;
    }

    @Transactional
    public void delete(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.USER_NOT_FOUND));
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.USER_NOT_FOUND));
        return new MemberResponseDto(member);
    }

    @Transactional(readOnly = true)
    public List<MemberListResponseDto> getAllMember(){
        return memberRepository.findAll().stream().map(member -> new MemberListResponseDto(member.getId(), member.getEmail(), member.getPassword(), member.getNickname())).collect(Collectors.toList());
    }

    @Transactional
    public String login(String id, String password) throws RuntimeException{
        Member member = memberRepository.findByEmail(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(password,member.getPassword())){
            throw new RuntimeException();// 비밀번호가 일치하지 않다는 예외 추가
        }
        String token = tokenProvider.createToken(String.valueOf(member.getId()),member.getRoles());

        return token;
    }
}
