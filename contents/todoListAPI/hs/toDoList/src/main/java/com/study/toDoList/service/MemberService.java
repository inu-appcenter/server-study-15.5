package com.study.toDoList.service;

import com.study.toDoList.domain.Member;
import com.study.toDoList.dto.MemberResponseDto;
import com.study.toDoList.dto.MemberSaveDto;
import com.study.toDoList.dto.MemberUpdateDto;
import com.study.toDoList.exception.ex.MyErrorCode;
import com.study.toDoList.exception.ex.MyNotFoundException;
import com.study.toDoList.exception.ex.MyNotValidException;
import com.study.toDoList.repositoy.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(String email, String password,String nickname){
        /*받은 비밀번호를 암호화*/
        /*if(memberRepository.findByEmail(email).isPresent()){
            throw new MyNotValidException(MyErrorCode.USER_NOT_VAlID);
        }*/
        return memberRepository.save(MemberSaveDto.builder().email(email).password(password).nickname(nickname).build().toEntity()).getId();
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
