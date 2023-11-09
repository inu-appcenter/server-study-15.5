package com.example.TodoProject.dto;

import com.example.TodoProject.entity.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
public class ResponseTodoGroupDto {

    private Long groupNum;

    @Schema(example = "중요한 일 모음")
    private String groupName;

    @Schema(example = "true")
    private Boolean isImportant;


    @Builder
    public ResponseTodoGroupDto(Long groupNum, String groupName, Boolean isImportant){
        this.groupNum = groupNum;
        this.groupName = groupName;
        this.isImportant = isImportant;

    }
}
