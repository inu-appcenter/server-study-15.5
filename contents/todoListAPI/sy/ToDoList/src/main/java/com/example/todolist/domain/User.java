package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Board> board;
    @Builder
    public User(String email, String name, String password){
        this.email=email;
        this.name=name;
        this.password=password;
    }
    public void changeName(String name){
        this.name=name;
    }

    public void changePassword(String password){
        this.password=password;
    }

}
