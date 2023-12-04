package com.study.toDoList;

import com.study.toDoList.domain.Member;
import com.study.toDoList.dto.MemberResponseDto;
import com.study.toDoList.dto.MemberSaveDto;
import com.study.toDoList.dto.MemberUpdateDto;
import com.study.toDoList.repositoy.MemberRepository;
import com.study.toDoList.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MemberServiceTest {

    private MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private MemberService memberService;

    @BeforeEach
    public void setUpTest(){
        memberService = new MemberService(memberRepository);
    }

    @Test
    void getMemberTest(){
        //given
        Member member = Member.builder().email("test@gmail.com").password("1234").nickname("test").build();

        Mockito.when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        MemberResponseDto memberResponseDto = memberService.getMember(1L);

        Assertions.assertThat(memberResponseDto.getEmail()).isEqualTo(member.getEmail());
        Assertions.assertThat(memberResponseDto.getPassword()).isEqualTo(member.getPassword());
        Assertions.assertThat(memberResponseDto.getNickname()).isEqualTo(member.getNickname());

        verify(memberRepository).findById(1L);
    }

    @Test
    void saveTest(){
        MemberSaveDto memberSaveDto = MemberSaveDto.builder().email("test@gmail.com").password("1234").nickname("test").build();
        Member member = Member.builder().email("test@gmail.com").password("1234").nickname("test").build();
        member.setIdForTest(123L);
        Mockito.when(memberRepository.save(any())).thenReturn(member);

        Long savedId = memberService.save(memberSaveDto);

        Assertions.assertThat(member.getId()).isEqualTo(savedId);
        verify(memberRepository).save(any());
    }

    @Test
    void updateTest(){
        MemberUpdateDto memberUpdateDto = MemberUpdateDto.builder().nickname("test1").password("1111").build();
        Member member = Member.builder().email("test@gmail.com").password("1234").nickname("test").build();
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));

        Long id = memberService.update(1L,memberUpdateDto);

        MemberResponseDto updatedMember = memberService.getMember(id);
        Assertions.assertThat(updatedMember.getNickname()).isEqualTo(memberUpdateDto.getNickname());
        Assertions.assertThat(updatedMember.getPassword()).isEqualTo(memberUpdateDto.getPassword());
    }

    @Test
    void deleteTest(){
        Member member = Member.builder().email("test@gmail.com").password("1234").nickname("test").build();
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));

        memberService.delete(123L);

        verify(memberRepository).delete(any());

    }

    @Test
    void getAllMemberTest(){
        memberService.getAllMember();
        verify(memberRepository).findAll();
    }
}
