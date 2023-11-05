package com.example.todolist.DTO.ToDo;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddToDoReqDTO {

    private String title;

    private String content;

    private LocalDateTime dueDate;

    private boolean isFinished;

    private Long userId;
}
