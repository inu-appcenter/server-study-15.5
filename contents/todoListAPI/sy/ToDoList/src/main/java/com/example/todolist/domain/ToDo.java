package com.example.todolist.domain;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toDoId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private boolean isFinished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "toDo",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Reply> reply;

    @Builder
    public ToDo(String title, LocalDateTime dueDate,String content,boolean isFinished ,User user){
        this.title=title;
        this.dueDate=dueDate;
        this.content=content;
        this.isFinished=isFinished;
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
