package com.study.toDoList.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateDto {
    private String password;
    private String nickname;

    @Builder
    public MemberUpdateDto(String password, String nickname){
        this.password = password;
        this.nickname = nickname;
    }
}
