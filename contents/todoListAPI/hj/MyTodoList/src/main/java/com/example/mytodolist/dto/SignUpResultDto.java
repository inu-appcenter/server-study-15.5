package com.example.mytodolist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class SignUpResultDto {

    private boolean success;
    private int code;
    private String msg;

    @Builder(builderMethodName = "signUpResultDtoBuilder")
    public SignUpResultDto(boolean success,int code, String msg)
    {
        this.success=success;
        this.code = code;
        this.msg= msg;
    }

}
