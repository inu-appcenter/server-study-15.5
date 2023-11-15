package com.example.TodoProject.dto.Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;

public class ClientResponseDto {

    @Getter
    @ApiModel("최경민멍청이")
    @ToString
    public static class ResponseClientDto {

        private Long clientNum;

        @Schema(example = "inu1234")
        @Column(name = "client_id")
        private String clientId;


        @Schema(example = "password1234")
        @Column(name="client_password")
        private String clientPassword;


        @Schema(example = "김정아")
        @Column(name = "client_name")
        private String clientName;


        @Schema(example = "example@google.com")
        @Column(name = "client_email")
        private String clientEmail;


        @Schema(example = "ROLE_USER")
        @Column(name = "client_role")
        private String clientRole;


        @Schema(example = "010-5420-9330")
        @Column(nullable = false, name = "client_phone_num")
        private String clientPhoneNum;

        @Builder
        public ResponseClientDto(Long clientNum, String clientId, String clientPassword, String clientName, String clientEmail, String clientRole,String clientPhoneNum){
            this.clientNum = clientNum;
            this.clientId = clientId;
            this.clientPassword = clientPassword;
            this.clientName = clientName;
            this.clientEmail = clientEmail;
            this.clientRole = clientRole;
            this.clientPhoneNum=clientPhoneNum;
        }
    }

}
