package com.study.toDoList.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate endDate;
    private Boolean isFinished;
    private Long memberId;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public TaskResponseDto(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.endDate = task.getEndDate();
        this.isFinished = task.getIsFinished();
        this.memberId = task.getMember().getId();
        this.createDate = task.getCreateDate();
        this.modifiedDate = task.getModifiedDate();
    }
}
