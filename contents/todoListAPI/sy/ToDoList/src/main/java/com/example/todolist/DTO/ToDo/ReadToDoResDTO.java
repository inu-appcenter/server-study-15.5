package com.example.todolist.DTO.ToDo;

import com.example.todolist.DTO.Reply.ReadReplyResDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadToDoResDTO {

    private String title;

    private String content;

    private List<ReadReplyResDTO> readReplyResDTOList;

    private LocalDateTime dueDate;

    private boolean isFinished;

    private String writerName;

    private boolean isMyToDo;

    private Long likeCnt;

}
