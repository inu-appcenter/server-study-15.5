package com.example.todolist.DTO.Reply;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeReplyReqDTO {

    private Long userId;

    private Long replyId;

    private String content;
}
