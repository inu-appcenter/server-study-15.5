package com.example.mytodolist.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_tb")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    //생성자로 초기화 하지 않으므로 기본값으로 0이 설정이 된다.
    //이후에 할일 성공 개수 기준으로 레벨을 증가시킴.
    @Column(nullable = false)
    private int level;

    //생성자로 초기화 하지 않아도, @Builder.Default 를 이용하여 빈 ArrayList 로 초기화 함.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Todo> todoList = new ArrayList<>();

    @Builder
    public User(String name, String email)
    {
        this.name =name;
        this.email = email;
        this.level = 0;
    }

    public void updateUserInfo(String name, String email){
        this.name = name;
        this.email = email;
    }

    //메서드 이름 변경.
    public void LevelUp(int level)
    {
        this.level = level;
    }

}
