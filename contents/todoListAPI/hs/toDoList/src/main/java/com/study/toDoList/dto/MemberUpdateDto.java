package com.study.toDoList.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberUpdateDto {
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;

    @Builder
    public MemberUpdateDto(String password, String nickname){
        this.password = password;
        this.nickname = nickname;
    }
}
