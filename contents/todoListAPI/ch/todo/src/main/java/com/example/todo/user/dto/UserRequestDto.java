package com.example.todo.user.dto;

import com.example.todo.groups.PValidationGroup;
import com.example.todo.groups.RUDValidationGroup;
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

    @Email(groups = { RUDValidationGroup.class, PValidationGroup.class })
    @NotBlank(groups = { RUDValidationGroup.class, PValidationGroup.class })
    @Size(max=50, groups = { RUDValidationGroup.class, PValidationGroup.class })
    @ApiModelProperty(value = "User 로그인 Id", notes = "String 문자열")
    private String loginId;

    @NotBlank(groups = { RUDValidationGroup.class, PValidationGroup.class })
    @Size(min=8, max=16, groups = { RUDValidationGroup.class, PValidationGroup.class })
    @ApiModelProperty(value = "User 로그인 password", notes = "String 문자열")
    private String password;

    @Size(min=2, max=15, groups = { RUDValidationGroup.class, PValidationGroup.class })
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
