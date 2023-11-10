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


    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Builder
    public Member (String email, String password,String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
    public void update(String password,String nickname){
        this.password = password;
        this.nickname = nickname;
    }
}
