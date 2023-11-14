package com.example.mytodolist.dto;


import com.example.mytodolist.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private int level;

    @Builder
    public UserResponseDto(Long id, String name, String email, int level){
        this.id = id;
        this.name = name;
        this.email = email;
        this.level = level;
    }

    //서비스에서 사용하던 변환 메서스를 dto로 옮김.
    public static UserResponseDto convertEntityToDto(User user)
    {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .level(user.getLevel())
                .build();
    }

}
