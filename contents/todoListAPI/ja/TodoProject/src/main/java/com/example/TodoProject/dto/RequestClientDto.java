package com.example.TodoProject.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Getter
public class RequestClientDto {

    private String clientId;

    private String clientPassword;

    private String clientName;

    private String clientEmail;

    private String clientRole;

    private String clientPhoneNum;

    @Builder
    public RequestClientDto(String clientId, String clientPassword, String clientName, String clientEmail, String clientRole,String clientPhoneNum){
        this.clientId = clientId;
        this.clientPassword = clientPassword;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientRole = clientRole;
        this.clientPhoneNum=clientPhoneNum;
    }
}
