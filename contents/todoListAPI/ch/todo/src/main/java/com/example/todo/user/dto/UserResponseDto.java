package com.example.todo.user.dto;

import com.example.todo.user.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@ApiModel("User 응답 DTO")
@Builder
@Getter
public class UserResponseDto {

    @ApiModelProperty(value = "User 식별자", notes = "Long 타입의 정수값")
    private Long userId;

    @ApiModelProperty(value = "User 로그인 Id", notes = "String 문자열")
    private String loginId;

    @ApiModelProperty(value = "User 로그인 password", notes = "String 문자열")
    private String password;

    @ApiModelProperty(value = "User 닉네임", notes = "String 문자열")
    private String name;

    private List<Authority> roles = new ArrayList<>();

    private String token;
}
