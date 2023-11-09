package com.example.todolist.DTO.ToDo;

import com.example.todolist.DTO.Reply.ReadReplyResDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadToDoResDTO {

    @ApiModelProperty(value = "ToDo 제목")
    private String title;

    @ApiModelProperty(value = "ToDo 내용")
    private String content;

    @ApiModelProperty(value = "ToDo에 달린 댓글리스트")
    private List<ReadReplyResDTO> readReplyResDTOList;

    @ApiModelProperty(value = "ToDo 마감일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH")
    private LocalDateTime dueDate;

    @ApiModelProperty(value = "ToDo 끝냈는지 여부", name = "isFinished")
    @JsonProperty("isFinished")
    private boolean isFinished;

    @ApiModelProperty(value = "ToDo 작성자 이름")
    private String writerName;

    @ApiModelProperty(value = "ToDo가 자신이 쓴 ToDo인지 여부")
    private boolean isMyToDo;

    @ApiModelProperty(value = "ToDo 좋아요 수")
    private Long likeCnt;

    @ApiModelProperty(value = "ToDo 작성일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm:SS")
    private LocalDateTime regDate;

    @ApiModelProperty(value = "ToDo 수정일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm:SS")
    private LocalDateTime modDate;
}
