package com.example.todolist.DTO.User;

import com.example.todolist.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserReqDTO {

    @ApiModelProperty(value = "유저 고유Id")
    @Null
    private Long userId; // userId값은 토큰에서 가져온다.

    @ApiModelProperty(value = "유저 이름")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "본인인증을 위한 기존 비밀번호")
    @NotBlank
    private String oldPassword;

    @ApiModelProperty(value = "변경할 비밀번호")
    @NotBlank
    private String newPassword;

    public static User changeUser(ChangeUserReqDTO changeUserReqDTO, User user){
        user.changeName(changeUserReqDTO.getName());
        user.changePassword(changeUserReqDTO.getNewPassword());

        return user;
    }
}
