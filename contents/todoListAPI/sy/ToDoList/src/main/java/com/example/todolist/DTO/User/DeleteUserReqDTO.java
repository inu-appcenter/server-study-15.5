package com.example.todolist.DTO.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserReqDTO {

    @ApiModelProperty(value = "유저 고유id")
    private Long userId; // userId값은 토큰에서 가져온다.

    @ApiModelProperty(value = "본인 인증을 위한 비밀번호")
    private String password;
}
