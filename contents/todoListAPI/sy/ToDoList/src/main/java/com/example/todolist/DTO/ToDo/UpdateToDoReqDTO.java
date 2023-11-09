package com.example.todolist.DTO.ToDo;

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
public class UpdateToDoReqDTO {

    @ApiModelProperty(value = "유저 고유id")
    private Long userId; // 토큰에서 userId값을 가져옴

    @ApiModelProperty(value = "ToDo 고유id")
    private Long toDoId;

    @ApiModelProperty(value = "ToDo 제목")
    private String title;

    @ApiModelProperty(value = "ToDo 내용")
    private String content;

    @ApiModelProperty(value = "ToDo 마감일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH")
    private LocalDateTime dueDate;

    @ApiModelProperty(value = "ToDo 끝냈는지 여부")
    @JsonProperty("isFinished")
    @Getter(AccessLevel.NONE)
    private boolean isFinished;

    public boolean getIsFinished(){
        return this.isFinished;
    }

}

