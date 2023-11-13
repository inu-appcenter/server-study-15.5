package com.example.todolist.DTO.Reply;

import com.example.todolist.domain.Reply;
import com.example.todolist.domain.ToDo;
import com.example.todolist.domain.User;
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

    public static Reply toEntity(AddReplyReqDTO addReplyReqDTO, User user, ToDo toDo){
        return Reply.builder()
                .content(addReplyReqDTO.getContent())
                .user(user)
                .toDo(toDo)
                .build();
    }
}
