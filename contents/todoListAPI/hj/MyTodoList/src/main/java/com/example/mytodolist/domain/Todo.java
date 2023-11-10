package com.example.mytodolist.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todo_tb")
public class Todo{

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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedDate;


    //당장 Time 슈퍼클래스를 사용할 이유가 없는 듯 하여 Todo클래스 필드에서 createdDate,modfiedDate 사용.
    @Builder
    public Todo(String title, String contents, LocalDateTime deadLine,Boolean isCompleted,User user){
        this.title = title;
        this.contents = contents;
        this.deadLine = deadLine;
        this.isCompleted = false;
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public void checkCompleted(){this.isCompleted = !isCompleted;}

    public void updateTodo(String title,String contents ,LocalDateTime deadLine)
    {
        this.title = title;
        this.contents = contents;
        this.deadLine = deadLine;
        this.modifiedDate = LocalDateTime.now();
    }

}
