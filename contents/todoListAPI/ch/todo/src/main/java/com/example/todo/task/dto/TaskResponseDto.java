package com.example.todo.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {
    private Long taskId;

    private Long userId;

    private String title;

    private String description;

    private LocalDateTime deadline;
}
