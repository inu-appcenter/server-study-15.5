package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String title;

    @Column
    private String content;

    @Builder
    public Board(String title, String content){
        this.title=title;
        this.content=content;
    }
}
