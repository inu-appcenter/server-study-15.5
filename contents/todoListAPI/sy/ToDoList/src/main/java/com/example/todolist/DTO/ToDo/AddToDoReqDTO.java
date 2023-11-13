package com.example.todolist.DTO.ToDo;
import com.example.todolist.domain.ToDo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToDoReqDTO {

    @ApiModelProperty(value = "ToDo 제목")
    private String title;

    @ApiModelProperty(value = "ToDo 내용")
    private String content;

    @ApiModelProperty(value = "ToDo 마감일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH")
    private LocalDateTime dueDate;

    @ApiModelProperty(value = "ToDo를 끝냈는지 여부")
    @JsonProperty("isFinished")
    @Getter(AccessLevel.NONE)
    private boolean isFinished;

    @ApiModelProperty(value = "유저 고유id")
    private Long userId; // 토큰에서 userId값을 가져옴

    public boolean getIsFinished(){
        return this.isFinished;
    }

    public static ToDo toEntity(AddToDoReqDTO addToDoReqDTO){

        ToDo toDo = ToDo.builder()
                .title(addToDoReqDTO.getTitle())
                .content(addToDoReqDTO.getContent())
                .dueDate(addToDoReqDTO.getDueDate())
                .isFinished(addToDoReqDTO.getIsFinished())
                .build();

        return toDo;
    }
}
