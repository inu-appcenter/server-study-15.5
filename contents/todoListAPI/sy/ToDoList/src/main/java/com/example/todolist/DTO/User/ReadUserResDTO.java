package com.example.todolist.DTO.User;

import com.example.todolist.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserResDTO {

    @ApiModelProperty(value = "유저 이메일")
    private String email;

    @ApiModelProperty(value = "유저 이름")
    private String name;

    public static ReadUserResDTO toDto(User user){

        return ReadUserResDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
