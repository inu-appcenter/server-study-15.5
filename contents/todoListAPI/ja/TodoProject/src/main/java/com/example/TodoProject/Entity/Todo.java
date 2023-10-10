package com.example.TodoProject.Entity;


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
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todo_Num;

    private String todo_Title;

    @Column(nullable = false)
    private String todo_Description;

    private LocalDate StartDate;

    private LocalDate EndDate;

    private Boolean isFinished;

    @Column(nullable = false)
    private String todo_Place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_Num")
    private Client client;

    @Builder
    public Todo(Long todo_Num,String todo_Title, String todo_Description,LocalDate StartDate, LocalDate EndDate, Boolean isFinished, String todo_Place){
        this.todo_Num = todo_Num;
        this.todo_Title = todo_Title;
        this.todo_Description = todo_Description;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.isFinished =isFinished;
        this.todo_Place = todo_Place;
    }


}
