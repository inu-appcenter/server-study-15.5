package com.example.todolist.DTO.Reply;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddReplyReqDTO {

    private Long userId;

    private Long toDoId;

    private String content;
}
