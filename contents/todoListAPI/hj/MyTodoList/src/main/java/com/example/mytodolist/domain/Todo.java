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

    //투두에 해당하는 유저를 할당합니다.
    //연관관계가 설정되어있어 this.user = user를 통해 엔티티간의 연관관계가 설정이 되면,
    //JPA는 이 변경을 인식하고 관련된 컬렉션에 업데이트를 자동으로 수행.
    public void assignToUser(User user){
        this.user = user;
    }
}
