package com.example.TodoProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class ShortClientDto {

    @Schema(example = "password1234")
    private String clientPassword;

    @Schema(example = "example@google.com")
    private String clientEmail;

    @Schema(example = "010-3068-9875")
    private String clientPhoneNum;
}
