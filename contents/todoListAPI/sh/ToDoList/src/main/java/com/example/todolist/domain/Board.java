package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime dueDate;

    @Column
    private boolean isFinished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Reply> reply;

    @Builder
    public Board(String title, LocalDateTime dueDate,String content,User user){
        this.title=title;
        this.dueDate=dueDate;
        this.content=content;
        this.user=user;
    }

    public void changeTitle(String title){
        this.title=title;
    }

    public void changeContent(String content){
        this.content=content;
    }

    public void changeDueDate(LocalDateTime dueDate){
        this.dueDate=dueDate;
    }

    public void changeIsFinished(boolean isFinished){
        this.isFinished=isFinished;
    }
}
