package com.study.toDoList.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String phone;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Builder
    public Member (String email, String password,String nickname, String name, String phone){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
    }

    public void changePassword(String password){
        this.password=password;
    }

    public void changeNickname(String nickname){
        this.nickname=nickname;
    }
}
