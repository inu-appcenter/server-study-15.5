package com.study.toDoList.service;

import com.study.toDoList.domain.Member;
import com.study.toDoList.dto.MemberResponseDto;
import com.study.toDoList.dto.MemberSaveDto;
import com.study.toDoList.dto.MemberUpdateDto;
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
    public Long save(MemberSaveDto memberSaveDto){
        //memberSaveDto.setPassword(passwordEncoder.encode(memberSaveDto.getPassword()));
        return memberRepository.save(memberSaveDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, MemberUpdateDto memberUpdateDto){
        Optional<Member> member = memberRepository.findById(id);
        member.get().update(memberUpdateDto.getPassword(),memberUpdateDto.getNickname());
        return id;
    }

    @Transactional
    public void delete(Long id){
        Optional<Member> member = memberRepository.findById(id);
        memberRepository.delete(member.get());
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long id){
        Optional<Member> member = memberRepository.findById(id);
        return new MemberResponseDto(member.get());
    }
}
