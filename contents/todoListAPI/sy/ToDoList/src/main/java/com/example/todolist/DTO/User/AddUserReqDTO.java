package com.example.todolist.DTO.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserReqDTO {

    @ApiModelProperty(value = "유저 이메일")
    private String email;

    @ApiModelProperty(value = "유저 이름")
    private String name;

    @ApiModelProperty(value = "유저 비밀번호")
    private String password;
}