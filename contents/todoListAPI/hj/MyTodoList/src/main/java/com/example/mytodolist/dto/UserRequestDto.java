package com.example.mytodolist.dto;

import com.example.mytodolist.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String name;
    private String email;


    @Builder
    public UserRequestDto (String name, String email)
    {
        this.name = name;
        this.email = email;
    }

}
