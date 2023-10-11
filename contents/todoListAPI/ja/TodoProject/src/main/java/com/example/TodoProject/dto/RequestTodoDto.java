package com.example.TodoProject.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RequestTodoDto {

    private String todoTitle;

    private String todoDescription;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isFinished;

    private String todoPlace;
    @Builder
    public RequestTodoDto(String todoTitle, String todoDescription,LocalDate startDate, LocalDate endDate, Boolean isFinished, String todoPlace){
        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFinished =isFinished;
        this.todoPlace = todoPlace;
    }

}
