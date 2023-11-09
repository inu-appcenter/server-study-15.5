package com.example.todolist.DTO.ToDo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadToDoPreviewResDTO {

    @ApiModelProperty(value = "ToDo 고유id")
    private Long toDoId;

    @ApiModelProperty(value = "ToDo 제목")
    private String title;

    @ApiModelProperty(value = "ToDo를 끝냈는지 여부")
    @JsonProperty("isFinished")
    private boolean isFinished;

    @ApiModelProperty(value = "ToDo 작성자 이름")
    private String writerName;

    @ApiModelProperty(value = "ToDo 좋아요 수")
    private Long likeCnt;

}
