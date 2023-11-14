package com.example.todo.user.dto;

import com.example.todo.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel("User 요청 DTO")
@Getter
public class UserRequestDto {

    @ApiModelProperty(value = "User 식별자", notes = "Long 타입의 정수값")
    private Long userId;

    @ApiModelProperty(value = "User 로그인 Id", notes = "String 문자열")
    private String loginId;

    @ApiModelProperty(value = "User 로그인 password", notes = "String 문자열")
    private String password;

    @ApiModelProperty(value = "User 닉네임", notes = "String 문자열")
    private String name;

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .loginId(this.loginId)
                .password(this.password)
                .build();
    }
}
