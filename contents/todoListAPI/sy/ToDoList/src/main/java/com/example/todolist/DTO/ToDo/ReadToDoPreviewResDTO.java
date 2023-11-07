package com.example.todolist.DTO.ToDo;

import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadToDoPreviewResDTO {

    private Long toDoId;

    private String title;

    private boolean isFinished;

    private String writerName;

    private Long likeCnt;

}
