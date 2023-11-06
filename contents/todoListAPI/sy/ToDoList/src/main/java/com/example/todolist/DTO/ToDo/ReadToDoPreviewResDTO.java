package com.example.todolist.DTO.ToDo;

import com.example.todolist.DTO.Reply.ReadReplyResDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReadToDoPreviewResDTO {

    private Long toDoId;

    private String title;

    private boolean isFinished;

    private String writerName;

    private Long likeCnt;

}
