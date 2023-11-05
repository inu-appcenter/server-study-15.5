package com.example.todo.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserRequestDto {

    private String loginId;

    private String password;

    private String name;

}
