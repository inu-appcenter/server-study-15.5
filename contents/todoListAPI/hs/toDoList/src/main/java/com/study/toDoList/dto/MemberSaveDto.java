package com.study.toDoList.dto;


import com.study.toDoList.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveDto {
    private String email;
    private String password;
    private String nickname;

    @Builder
    public MemberSaveDto(String email,String password,String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
