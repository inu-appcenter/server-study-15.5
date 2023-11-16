package com.example.todo.user.dto;

import com.example.todo.common.ValidationGroup.RUDValidationGroup;
import com.example.todo.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@ApiModel("User 요청 DTO")
@Getter
public class UserRequestDto {

    @Positive(groups = { RUDValidationGroup.class})
    @ApiModelProperty(value = "User 식별자", notes = "Long 타입의 정수값")
    private Long userId;

    @Email
    @NotBlank
    @Size(min=6, max=50)
    @ApiModelProperty(value = "User 로그인 Id", notes = "String 문자열")
    private String loginId;

    @NotBlank
    @Size(min=6, max=30)
    @ApiModelProperty(value = "User 로그인 password", notes = "String 문자열")
    private String password;

    @Size(min=2, max=15)
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
