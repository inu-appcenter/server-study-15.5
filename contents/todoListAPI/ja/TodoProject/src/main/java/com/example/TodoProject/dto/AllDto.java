package com.example.TodoProject.dto;

import com.example.TodoProject.entity.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AllDto {
    @Schema(example = "중요한 일 모음")
    private String groupName;

    @Schema(example = "true")
    private Boolean isImportant;

    private List<ResponseTodoDto> todoList;

    @Builder
    public AllDto(String groupName, Boolean isImportant, List<ResponseTodoDto> todoList){
        this.groupName = groupName;
        this.isImportant = isImportant;
        this.todoList = todoList;
    }
}
