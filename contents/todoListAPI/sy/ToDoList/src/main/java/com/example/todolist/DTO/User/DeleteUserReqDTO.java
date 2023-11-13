package com.example.todolist.DTO.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserReqDTO {

    @ApiModelProperty(value = "유저 고유id")
    @Null
    private Long userId; // userId값은 토큰에서 가져온다.

    @ApiModelProperty(value = "본인 인증을 위한 비밀번호")
    @NotBlank
    private String password;
}
