package com.study.toDoList;

import com.study.toDoList.domain.Member;
import com.study.toDoList.exception.ex.MyErrorCode;
import com.study.toDoList.exception.ex.MyNotFoundException;
import com.study.toDoList.repositoy.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


@DataJpaTest
@TestPropertySource("classpath:application_test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 테스트")
    void saveTest(){
        //given
        Member member = Member.builder().email("test@gmail.com").password("1234").nickname("test").build();

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Assertions.assertThat(member.getEmail()).isEqualTo(savedMember.getEmail());
        Assertions.assertThat(member.getPassword()).isEqualTo(savedMember.getPassword());
        Assertions.assertThat(member.getNickname()).isEqualTo(savedMember.getNickname());
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void selectTest(){
        //given
        Member member = Member.builder().email("test@gmail.com").password("1234").nickname("test").build();

        Member savedMember = memberRepository.save(member);

        //when
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        //then
        Assertions.assertThat(member.getEmail()).isEqualTo(foundMember.getEmail());
        Assertions.assertThat(member.getPassword()).isEqualTo(foundMember.getPassword());
        Assertions.assertThat(member.getNickname()).isEqualTo(foundMember.getNickname());

    }

    @Test
    @DisplayName("회원 업데이트 테스트")
    void updateTest(){
        //given
        Member member = Member.builder().email("test@gmail.com").password("1234").nickname("test").build();
        Member savedMember = memberRepository.save(member);


        //when
        savedMember.setEmailForTest("test123@gmail.com");
        memberRepository.save(savedMember);
        Member updateMember = memberRepository.findById(savedMember.getId()).get();


        //then
        Assertions.assertThat(updateMember.getEmail()).isEqualTo("test123@gmail.com");

    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteTest(){
        //given
        Member member = Member.builder().email("test@gmail.com").password("1234").nickname("test").build();

        Member savedMember = memberRepository.save(member);

        //when
        memberRepository.delete(savedMember);

        //then
        org.junit.jupiter.api.Assertions.assertFalse(memberRepository.findById(savedMember.getId()).isPresent());
    }
}
