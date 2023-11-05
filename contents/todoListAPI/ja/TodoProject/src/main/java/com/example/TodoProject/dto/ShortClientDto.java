package com.example.TodoProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class ShortClientDto {

    @Schema(example = "password1234")
    @Column(name="client_password")
    private String clientPassword;

    @Schema(example = "example@google.com")
    @Column(name = "client_email")
    private String clientEmail;

    @Schema(example = "010-3068-9875")
    @Column(nullable = false, name = "client_phone_num")
    private String clientPhoneNum;
}
