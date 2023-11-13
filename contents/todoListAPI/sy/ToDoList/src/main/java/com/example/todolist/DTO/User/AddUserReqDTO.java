package com.example.todolist.DTO.User;

import com.example.todolist.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserReqDTO {

    @ApiModelProperty(value = "유저 이메일")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(value = "유저 이름")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "유저 비밀번호")
    @NotBlank
    private String password;

    public static User toEntity(AddUserReqDTO addUserReqDTO){

        User user = User.builder()
                .name(addUserReqDTO.getName())
                .email(addUserReqDTO.getEmail())
                .password(addUserReqDTO.getPassword())
                .build();

        return user;
    }
}
