package com.example.todo.user.dto;

import lombok.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long userId;

    private String loginId;

    private String password;

    private String name;

}
