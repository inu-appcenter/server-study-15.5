package com.study.toDoList.dto;

import com.study.toDoList.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private String email;
    private String password;
    private String nickname;

    public MemberResponseDto(Member member){
        this.email =member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
    }
}
