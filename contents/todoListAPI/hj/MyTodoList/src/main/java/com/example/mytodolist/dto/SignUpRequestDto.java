package com.example.mytodolist.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "아이디를 입력해 주세요")
    @ApiModelProperty(value = "사용자 아이디", example = "wldbs4746")
    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-zA-Z0-9]+$")
    private String uid;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Size(min = 8, max = 20)
    @ApiModelProperty(value = "사용자 비밀번호", example = "123456789k")
    //^ = 위치를 맨 앞으로 가져온다
    //?=  = positive lookahead로 뒤에 있는 조건을 만족해야 한다.
    //.*[A-Za-z] = 앞에 나온 조건으로 글자에 a~z, A~Z 중 1개를 만족하는지 본다.
    //?=.*\d = 글자가 숫자를 포함하는지 확인한다.
    //[A-Za-z\d]{8,}$ = 끝까지 문자 + 숫자의 조합이 8자 이상인지 확인한다.
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",message = "영어문자 + 숫자 조합으로 8~20 자 사이로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @ApiModelProperty(value = "사용자 이름", example = "홍길순") //문서화 정보를 제공
    @Size(min = 2, max=10)
    @Pattern(regexp = "[가-힣]+$", message = "이름은 한글로 2자 이상 입력해야합니다.")
    private String name;

    @NotBlank(message = "이메일을 입력해 주세요")
    @ApiModelProperty(value = "이메일", example = "stronghj@naver.com")
    @Email(message = "이메일 형식을 맞춰주세요")
    private String email;

    @Builder
    public SignUpRequestDto(String uid,String password, String name,String email)
    {
        this.uid = uid;
        this.password = password;
        this.email = email;
        this.name = name;
    }

}
