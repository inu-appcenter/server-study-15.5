package com.example.TodoProject.dto.Client;

import com.example.TodoProject.entity.Client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;
import java.util.List;

public class ClientRequestDto {

    @Getter
    public static class RequestClientDto {

        @Schema(description = "클라이언트가 로그인 할 때 사용하는 아이디",example = "inu1234")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Size(min = 5, max = 15, message="아이디는 최소 5자 이상, 최대 15자 이하의 길이를 충족해야 합니다.")
        private String clientId;

        @Schema(description = "클라이언트가 로그인 할 때 사용하는 비밀번호", example = "password1234@")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,20}", message = "비밀번호는 최소 5자 이상, 최대 20자 이하의 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
        private String clientPassword;

        @Schema(description = "클라이언트 실제 이름. 닉네임아님주의.", example = "홍길동")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String clientName;

        @Schema(description = "클라이언트 이메일",example = "example@google.com")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email
        private String clientEmail;

        @Schema(description = "클라이언트의 권한역할. 일반 유저는 ROLE_USER, 관리자는 ROLE_ADMIN", example = "[\"ROLE_USER\"]")
        @NotNull(message = "유저 권한은 필수 입력 값입니다.")
        private List<String> clientRole;

        @Schema(description = "클라이언트 전화번호", example = "010-1234-5678")
        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^[0-9].{3}+[0-9].{3,4}+[0-9].{3,4}$")
        private String clientPhoneNum;

        public Client toEntity(RequestClientDto requestClientDto, String password){
            return Client.builder()
                    .clientId(requestClientDto.getClientId())
                    .clientPassword(password)
                    .clientName(requestClientDto.getClientName())
                    .clientEmail(requestClientDto.getClientEmail())
                    .clientRole(requestClientDto.getClientRole())
                    .clientPhoneNum(requestClientDto.getClientPhoneNum())
                    .build();
        }

        @Builder
        public RequestClientDto(String clientId, String clientPassword, String clientName, String clientEmail, List<String> clientRole, String clientPhoneNum){
            this.clientId = clientId;
            this.clientPassword = clientPassword;
            this.clientName = clientName;
            this.clientEmail = clientEmail;
            this.clientRole = clientRole;
            this.clientPhoneNum=clientPhoneNum;
        }
    }

    @Getter
    public static class EditClientDto {

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,20}", message = "비밀번호는 최소 5자 이상, 최대 20자 이하의 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
        @Schema(description = "클라이언트가 로그인 할 때 사용하는 비밀번호", example = "password1234@")
        private String clientPassword;

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email
        @Schema(description = "클라이언트 이메일",example = "example@google.com")
        private String clientEmail;

        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^\\d{3,4}-\\d{3,4}-\\d{4}$")
        @Schema(description = "클라이언트 전화번호", example = "010-5420-9330")
        private String clientPhoneNum;

        @Builder
        public EditClientDto(String clientPassword, String clientName, String clientEmail, String clientRole, String clientPhoneNum){
            this.clientPassword = clientPassword;
            this.clientEmail = clientEmail;
            this.clientPhoneNum=clientPhoneNum;
        }
    }
    @Getter
    public static class RequestLoginDto {

        @Schema(description = "클라이언트가 로그인 할 때 사용하는 아이디",example = "inu1234")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Size(min = 5, max = 15, message="아이디는 최소 5자 이상, 최대 15자 이하의 길이를 충족해야 합니다.")
        private String clientId;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,20}", message = "비밀번호는 최소 5자 이상, 최대 20자 이하의 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
        @Schema(description = "클라이언트가 로그인 할 때 사용하는 비밀번호", example = "password1234@")
        private String clientPassword;

    }

}
