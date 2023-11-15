package com.study.toDoList.service;

import com.study.toDoList.domain.Member;
import com.study.toDoList.dto.MemberResponseDto;
import com.study.toDoList.dto.MemberSaveDto;
import com.study.toDoList.dto.MemberUpdateDto;
import com.study.toDoList.exception.ex.MyDuplicateException;
import com.study.toDoList.exception.ex.MyErrorCode;
import com.study.toDoList.exception.ex.MyNotFoundException;
import com.study.toDoList.repositoy.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(MemberSaveDto memberSaveDto){
        String encodedPassword = memberSaveDto.getPassword();/*받은 비밀번호 암호화*/
        if(memberRepository.existsByEmail(memberSaveDto.getEmail())){
            throw new MyDuplicateException(MyErrorCode.USER_DUPLICATE_EMAIL);
        }
        return memberRepository.save(MemberSaveDto.builder().email(memberSaveDto.getEmail()).password(encodedPassword).nickname(memberSaveDto.getNickname()).build().toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, MemberUpdateDto memberUpdateDto){
        Member member = memberRepository.findById(id).orElseThrow(()->new MyNotFoundException(MyErrorCode.USER_NOT_FOUND));
        member.update(memberUpdateDto.getPassword(),memberUpdateDto.getNickname());
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
}
