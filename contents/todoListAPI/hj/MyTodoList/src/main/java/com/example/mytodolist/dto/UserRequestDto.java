package com.example.mytodolist.dto;

import com.example.mytodolist.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // Json @RequestBody 형식으로 데이터를 입력받고, 객체화 하려면 dto에 기본 생성자가 있어야한다.
public class UserRequestDto {

    private String name;
    private String email;


    @Builder
    public UserRequestDto (String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    //서비스에서 사용하던 변환 메서스를 dto로 옮김.
    public static User DtoToEntity(UserRequestDto userRequestDto)
    {
        return User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .build();
    }

}
