package com.study.toDoList.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskUpdateDto {
    private String title;
    private String description;
    private LocalDateTime endDate;
    private Boolean isFinished;
    @Builder
    public TaskUpdateDto(String title, String description, LocalDateTime endDate, Boolean isFinished){
        this.title =title;
        this.description = description;
        this.endDate = endDate;
        this.isFinished = isFinished;
    }
}
