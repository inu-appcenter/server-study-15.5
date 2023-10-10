package com.example.mytodolist.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todo_tb")
public class Todo extends Time{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private  String contents;

    //생성자로 초기화 하지 않으므로 기본값으로 false 가 들어간다.
    @Column(nullable = false)
    private Boolean isCompleted;

    @Column(nullable = false)
    private LocalDateTime deadLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false) //외래키 설정.
    private User user;

    @Builder
    public Todo(String title, String contents, LocalDateTime deadLine){
        this.title = title;
        this.contents = contents;
        this.deadLine = deadLine;
    }

    //투두에 해당하는 유저 엔티티를 저장.
    public void setUser(User user){
        this.user = user;
        user.getTodoList().add(this);
    }
}
