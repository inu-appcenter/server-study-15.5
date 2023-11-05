package com.example.todolist.DTO.ToDo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateToDoReqDTO {

    private Long userId; // 토큰에서 userId값을 가져옴

    private Long toDoId;

    private String title;

    private String content;

    private LocalDateTime dueDate;

    private boolean isFinished;

}

