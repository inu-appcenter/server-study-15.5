package com.study.toDoList.dto;

import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class TaskListResponseDto {
    private Long id;
    private String title;
    private boolean isFinished;
    private Long memberId;
    private LocalDateTime modifiedDate;

    public TaskListResponseDto(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.isFinished = task.isFinished();
        this.memberId = task.getMember().getId();
        this.modifiedDate = task.getModifiedDate();
    }
}
