package com.study.toDoList.dto;

import com.study.toDoList.domain.Task;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberListResponseDto {
    public Long id;
    public String email;
    public String password;
    public String nickname;

    public MemberListResponseDto(Long id, String email, String password, String nickname){
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
