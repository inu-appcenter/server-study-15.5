package com.example.todo.task.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskDto {
    private Long userId;

    private String title;

    private String description;

    private LocalDateTime deadline;
}
