package com.example.todolist.DTO.Reply;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReplyReqDTO {

    @ApiModelProperty(value = "유저 고유id")
    private Long userId;

    @ApiModelProperty(value = "ToDo 고유id")
    private Long toDoId;

    @ApiModelProperty(value = "댓글 내용")
    private String content;
}
