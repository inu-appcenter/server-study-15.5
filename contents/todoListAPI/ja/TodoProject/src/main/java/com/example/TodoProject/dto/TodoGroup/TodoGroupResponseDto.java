package com.example.TodoProject.dto.TodoGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.example.TodoProject.dto.Todo.TodoRequestDto.*;
import static com.example.TodoProject.dto.Todo.TodoResponseDto.*;

public class TodoGroupResponseDto {

    @Getter
    public static class TodoListDto {
        @Schema(example = "중요한 일 모음")
        private String groupName;

        @Schema(example = "true")
        private Boolean isImportant;

        private List<ResponseTodoDto> todoList;

        @Builder
        public TodoListDto(String groupName, Boolean isImportant, List<ResponseTodoDto> todoList){
            this.groupName = groupName;
            this.isImportant = isImportant;
            this.todoList = todoList;
        }
    }

    @Getter
    public static class ResponseTodoGroupDto {

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

}
