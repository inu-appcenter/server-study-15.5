package com.example.todolist.DTO.ToDo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateToDoReqDTO {

    private Long userId; // 토큰에서 userId값을 가져옴

    private Long toDoId;

    private String title;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH")
    private LocalDateTime dueDate;

    private boolean isFinished;

}

