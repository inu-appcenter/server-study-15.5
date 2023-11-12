package com.study.toDoList.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskUpdateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @FutureOrPresent
    private LocalDateTime endDate;
    @NotBlank
    private Boolean isFinished;
    @Builder
    public TaskUpdateDto(String title, String description, LocalDateTime endDate, Boolean isFinished){
        this.title =title;
        this.description = description;
        this.endDate = endDate;
        this.isFinished = isFinished;
    }
}
