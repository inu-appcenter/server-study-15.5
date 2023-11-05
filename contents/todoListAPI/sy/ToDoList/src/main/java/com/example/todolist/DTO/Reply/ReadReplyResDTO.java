package com.example.todolist.DTO.Reply;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReadReplyResDTO {

    private Long replyId;

    private String writer;

    private String content;
}
