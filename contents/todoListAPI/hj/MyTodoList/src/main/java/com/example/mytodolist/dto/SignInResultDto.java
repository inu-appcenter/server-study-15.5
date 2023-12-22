package com.example.mytodolist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInResultDto extends SignUpResultDto{

    private String token;

    @Builder(builderMethodName = "signInResultDtoBuilder")
    public SignInResultDto(Long id, String uid,String name, String email, int level, String token){
        super(id,uid,name,email,level);
        this.token = token;
    }

}
