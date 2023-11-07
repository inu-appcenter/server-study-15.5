package com.example.todolist.DTO.Reply;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeReplyReqDTO {

    @ApiModelProperty(value = "유저 고유id")
    private Long userId;

    @ApiModelProperty(value = "댓글 고유id")
    private Long replyId;

    @ApiModelProperty(value = "댓글 내용")
    private String content;
}
