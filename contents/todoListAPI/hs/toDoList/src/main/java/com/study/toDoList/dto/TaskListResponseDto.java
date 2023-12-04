package com.study.toDoList.dto;

import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class TaskListResponseDto {
    private Long id;
    private String title;
    private Boolean isFinished;
    private Long memberId;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;


    public TaskListResponseDto(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.isFinished = task.getIsFinished();
        this.memberId = task.getMember().getId();
        this.createDate = task.getCreateDate();
        this.modifiedDate = task.getModifiedDate();
    }
}
