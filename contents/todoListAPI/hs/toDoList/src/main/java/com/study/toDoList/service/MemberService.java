package com.study.toDoList.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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
        if(memberRepository.existsByNickname(memberSaveDto.getNickname())){
            throw new MyDuplicateException(MyErrorCode.USER_DUPLICATE_NICKNAME);
        }
        return memberRepository.save(MemberSaveDto.builder().email(memberSaveDto.getEmail()).password(encodedPassword).nickname(memberSaveDto.getNickname()).build().toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, MemberUpdateDto memberUpdateDto){
        Member member = memberRepository.findById(id).orElseThrow(()->new MyNotFoundException(MyErrorCode.USER_NOT_FOUND));
        if(memberRepository.existsByNickname(memberUpdateDto.getNickname())){
            throw new MyDuplicateException(MyErrorCode.USER_DUPLICATE_NICKNAME);
        }
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

    @Transactional(readOnly = true)
    public List<MemberListResponseDto> getAllMember(){
        return memberRepository.findAll().stream().map(member -> new MemberListResponseDto(member.getId(), member.getEmail(), member.getPassword(), member.getNickname())).collect(Collectors.toList());
    }
}
