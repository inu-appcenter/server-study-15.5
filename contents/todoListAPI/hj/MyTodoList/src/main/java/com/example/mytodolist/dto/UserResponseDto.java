package com.example.mytodolist.dto;


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

}
