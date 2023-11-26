package com.example.mytodolist.dto;

import com.example.mytodolist.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor // Json @RequestBody 형식으로 데이터를 입력받고, 객체화 하려면 dto에 기본 생성자가 있어야한다.
public class UserRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    @ApiModelProperty(value = "사용자 이름", example = "홍길순") //문서화 정보를 제공
    @Size(min = 2, max=10)
    @Pattern(regexp = "[가-힣]+$")
    private String name;

    @NotBlank(message = "이메일을 입력해 주세요")
    @ApiModelProperty(value = "이메일", example = "stronghj@naver.com")
    @Email(message = "이메일 형식을 맞춰주세요")
    private String email;


    @Builder
    public UserRequestDto (String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    //서비스에서 사용하던 변환 메서스를 dto로 옮김.
    public static User convertDtoToEntity(UserRequestDto userRequestDto)
    {
        return User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .build();
    }

}
