package com.example.todo.user.dto;

import com.example.todo.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel("User 요청 DTO")
@Getter
public class UserRequestDto {

    @ApiModelProperty(value = "User 로그인 Id", notes = "String 문자열")
    private String loginId;

    @ApiModelProperty(value = "User 로그인 password", notes = "String 문자열")
    private String password;

    @ApiModelProperty(value = "User 닉네임", notes = "String 문자열")
    private String name;

    public static User toEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .name(userRequestDto.name)
                .loginId(userRequestDto.loginId)
                .password(userRequestDto.password)
                .build();
    }
}
