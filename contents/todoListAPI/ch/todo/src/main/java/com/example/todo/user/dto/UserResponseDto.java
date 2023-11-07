package com.example.todo.user.dto;

import lombok.*;


@Builder
@Getter
public class UserResponseDto {

    private Long userId;

    private String loginId;

    private String password;

    private String name;

}
