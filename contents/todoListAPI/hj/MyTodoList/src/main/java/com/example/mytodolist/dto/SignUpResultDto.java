package com.example.mytodolist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResultDto {

    private Long id;
    private String uid;
    private String name;
    private String email;
    private int level;


    @Builder
    public SignUpResultDto (Long id, String uid, String name, String email, int level)
    {
        this.id=id;
        this.uid = uid;
        this.name = name;
        this.email=email;
        this.level = level;
    }
}
