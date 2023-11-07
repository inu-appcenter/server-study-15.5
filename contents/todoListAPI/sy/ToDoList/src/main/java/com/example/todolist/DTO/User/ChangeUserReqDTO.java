package com.example.todolist.DTO.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserReqDTO {

    @ApiModelProperty(value = "유저 고유Id")
    private Long userId; // userId값은 토큰에서 가져온다.

    @ApiModelProperty(value = "유저 이름")
    private String name;

    @ApiModelProperty(value = "본인인증을 위한 기존 비밀번호")
    private String oldPassword;

    @ApiModelProperty(value = "변경할 비밀번호")
    private String newPassword;
}
