package com.example.mytodolist.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @Column(nullable = false)//데이터베이스에는 camel 형식이 아니기 때문에 조치를 취해야한다.
    private Boolean completed;

    @Column(nullable = false)
    private LocalDateTime deadLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false) //외래키 설정.
    private User user;


    //당장 Time 슈퍼클래스를 사용할 이유가 없는 듯 하여 Todo클래스 필드에서 createdDate,modfiedDate 사용.(취소)
    @Builder
    public Todo(String title, String contents, LocalDateTime deadLine,User user){
        this.title = title;
        this.contents = contents;
        this.deadLine = deadLine;
        this.completed = false;
        this.user = user;
    }

    public void checkCompleted(){this.completed = !completed;}

    public void updateTodo(String title,String contents ,LocalDateTime deadLine)
    {
        this.title = title;
        this.contents = contents;
        this.deadLine = deadLine;
    }

}
