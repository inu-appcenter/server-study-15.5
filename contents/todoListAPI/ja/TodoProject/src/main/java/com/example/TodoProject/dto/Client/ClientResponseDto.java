package com.example.TodoProject.dto.Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import java.util.List;

public class ClientResponseDto {

    @Getter
    public static class ResponseClientDto {

        private Long clientNum;

        @Schema(example = "inu1234", description = "회원가입 할 아이디")
        private String clientId;


        @Schema(example = "password1234@", description = "회원가입 할 유저가 입력한 비밀번호")
        private String clientPassword;


        @Schema(example = "홍길동", description = "회원가입하는 유저의 이름")
        private String clientName;


        @Schema(example = "example@google.com")
        private String clientEmail;


        @Schema(example = "ROLE_USER")
        private List<String> clientRole;


        @Schema(example = "010-1234-5678")
        private String clientPhoneNum;

        @Builder
        public ResponseClientDto(Long clientNum, String clientId, String clientPassword, String clientName, String clientEmail, List<String> clientRole,String clientPhoneNum){
            this.clientNum = clientNum;
            this.clientId = clientId;
            this.clientPassword = clientPassword;
            this.clientName = clientName;
            this.clientEmail = clientEmail;
            this.clientRole = clientRole;
            this.clientPhoneNum=clientPhoneNum;
        }
    }

    @Getter
    public static class ResponseClientSignDto{

        @Schema(example = "2", description = "유저의 데이터베이스 상 번호가 들어감니다.")
        private String clientNum;

    }

    @Getter
    public static class ResponseClientListDto{
        private List<ResponseClientDto> data;
    }

}
