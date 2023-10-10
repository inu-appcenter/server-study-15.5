package com.example.TodoProject.entity;


import com.example.TodoProject.common.Time;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "Todo_tb")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoNum;

    private String todoTitle;

    @Column(nullable = false)
    private String todoDescription;

    private LocalDate StartDate;

    private LocalDate EndDate;

    private Boolean isFinished;

    @Column(nullable = false)
    private String todoPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_Num")
    private Client client;

    @Builder
    public Todo(Long todoNum,String todoTitle, String todoDescription,LocalDate StartDate, LocalDate EndDate, Boolean isFinished, String todoPlace){
        this.todoNum = todoNum;
        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.isFinished =isFinished;
        this.todoPlace = todoPlace;
    }
}
