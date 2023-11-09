package com.example.TodoProject.dto.Client;

import com.example.TodoProject.entity.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

public class ClientRequestDto {

    @Getter
    public static class RequestClientDto {

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
        public RequestClientDto(String clientId, String clientPassword, String clientName, String clientEmail, String clientRole,String clientPhoneNum){
            this.clientId = clientId;
            this.clientPassword = clientPassword;
            this.clientName = clientName;
            this.clientEmail = clientEmail;
            this.clientRole = clientRole;
            this.clientPhoneNum=clientPhoneNum;
        }

        public Client toEntity(RequestClientDto requestClientDto){
            return Client.builder()
                    .clientId(requestClientDto.getClientId())
                    .clientPassword(requestClientDto.getClientPassword())
                    .clientName(requestClientDto.getClientName())
                    .clientEmail(requestClientDto.getClientEmail())
                    .clientRole(requestClientDto.getClientRole())
                    .clientPhoneNum(requestClientDto.getClientPhoneNum())
                    .build();
        }
    }

    @Getter
    public static class EditClientDto {

        @Schema(example = "password1234")
        private String clientPassword;

        @Schema(example = "example@google.com")
        private String clientEmail;

        @Schema(example = "010-3068-9875")
        private String clientPhoneNum;
    }

}
