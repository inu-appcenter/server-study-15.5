package com.example.todo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel("User 응답 DTO")
@Builder
@Getter
public class UserResponseDto {

    @NotBlank
    @ApiModelProperty(value = "User 식별자", notes = "Long 타입의 정수값")
    private Long userId;

    @ApiModelProperty(value = "User 로그인 Id", notes = "String 문자열")
    private String loginId;

    @ApiModelProperty(value = "User 로그인 password", notes = "String 문자열")
    private String password;

    @Size(min=2, max=15)
    @ApiModelProperty(value = "User 닉네임", notes = "String 문자열")
    private String name;

}
