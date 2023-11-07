package com.example.todo.task.dto;

import com.example.todo.task.Task;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TaskResponseDto {
    private Long taskId;

    private Long userId;

    private String title;

    private String description;

    private LocalDateTime deadline;

    public TaskResponseDto(Task task) {
        this.taskId = task.getTaskId();

        // 이렇게되면 ReponseDTO에 필요없는 User 정보가 포함되지는 않나?
        // 로딩시점을 Lazy로 설정해놓고도, DTO를 만들기 위해 User 엔티티를 조회하게 되는거 아닌가.
        this.userId = task.getUser().getUserId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.deadline = task.getDeadline();
    }
}
