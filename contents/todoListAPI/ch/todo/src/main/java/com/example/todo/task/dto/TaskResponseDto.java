package com.example.todo.task.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
public class TaskResponseDto {
    private Long taskId;

    private Long userId;

    private String title;

    private String description;

    private LocalDateTime deadline;
}
