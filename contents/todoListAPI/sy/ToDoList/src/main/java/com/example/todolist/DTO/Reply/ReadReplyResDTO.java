package com.example.todolist.DTO.Reply;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadReplyResDTO {

    private Long replyId;

    private String writer;

    private String content;

    private boolean isMyReply;
}
