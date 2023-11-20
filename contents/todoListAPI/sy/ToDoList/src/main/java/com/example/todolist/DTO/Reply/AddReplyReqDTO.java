package com.example.todolist.DTO.Reply;

import com.example.todolist.domain.Reply;
import com.example.todolist.domain.ToDo;
import com.example.todolist.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReplyReqDTO {

    @ApiModelProperty(value = "유저 고유id")
    @Null
    private Long userId;

    @ApiModelProperty(value = "ToDo 고유id")
    private Long toDoId;

    @ApiModelProperty(value = "댓글 내용")
    @NotBlank
    private String content;

    public static Reply toEntity(AddReplyReqDTO addReplyReqDTO, User user, ToDo toDo){
        return Reply.builder()
                .content(addReplyReqDTO.getContent())
                .user(user)
                .toDo(toDo)
                .build();
    }
}
