package com.example.todolist.DTO.Reply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadReplyResDTO {

    @ApiModelProperty(value = "댓글 고유id")
    private Long replyId;

    @ApiModelProperty(value = "댓글 작성자 이름")
    private String writer;

    @ApiModelProperty(value = "댓글 내용")
    private String content;

    @ApiModelProperty(value = "댓글이 자신이 쓴 댓글인지 여부")
    private boolean isMyReply;

    @ApiModelProperty(value = "댓글 작성일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm:SS")
    private LocalDateTime regDate;

    @ApiModelProperty(value = "댓글 수정일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm:SS")
    private LocalDateTime modDate;
}
