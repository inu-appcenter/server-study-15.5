package com.example.todolist.DTO.ToDo;

import com.example.todolist.DTO.Reply.ReadReplyResDTO;
import com.example.todolist.domain.Reply;
import com.example.todolist.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReadToDoResDTO {

    private Long toDoId;

    private String title;

    private String content;

    private List<ReadReplyResDTO> readReplyResDTOList;

    private LocalDateTime dueDate;

    private boolean isFinished;

    private String writerName;

    private boolean isMyToDo;

    private Long likeCnt;

}
