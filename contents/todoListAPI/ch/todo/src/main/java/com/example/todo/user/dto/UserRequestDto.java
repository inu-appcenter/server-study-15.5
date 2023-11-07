package com.example.todo.user.dto;

import com.example.todo.user.User;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private String loginId;

    private String password;

    private String name;

    public static User toEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .name(userRequestDto.name)
                .loginId(userRequestDto.loginId)
                .password(userRequestDto.password)
                .build();
    }
}
