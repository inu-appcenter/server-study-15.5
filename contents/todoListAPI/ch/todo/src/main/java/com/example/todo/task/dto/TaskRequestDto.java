package com.example.todo.task.dto;

import com.example.todo.task.Task;
import com.example.todo.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskRequestDto {

    private Long taskId;

    private Long userId;

    private String title;

    private String description;

    private LocalDateTime deadline;

    public static Task toEntity(TaskRequestDto taskRequestDto, User user) {
        return Task.builder()
                .title(taskRequestDto.title)
                .description(taskRequestDto.description)
                .deadline(taskRequestDto.deadline)
                .user(user)
                .build();
    }
}
