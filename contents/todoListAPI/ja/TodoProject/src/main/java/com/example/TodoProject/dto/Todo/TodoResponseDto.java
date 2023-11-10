package com.example.TodoProject.dto.Todo;

import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class TodoResponseDto {
    @Getter
    public static class ResponseTodoDto {

        private Long todoNum;

        @Schema(example = "도서관 가서 라면먹기")
        private String todoTitle;

        @Schema(example = "도서관에 가서 신라면에 꿀단지를 먹을 것이다.")
        private String todoDescription;

        private LocalDate startDate;

        private LocalDate endDate;

        @Schema(example = "False")
        private Boolean isFinished;

        @Schema(example = "도서관 지하열람실 이마트24")
        private String todoLocation;

        private Object todoGroupNum;


        @Builder
        public ResponseTodoDto(Long todoNum, String todoTitle, String todoDescription, LocalDate startDate, LocalDate endDate, Boolean isFinished, String todoLocation, Object todoGroupNum) {
            this.todoNum = todoNum;
            this.todoTitle = todoTitle;
            this.todoDescription = todoDescription;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isFinished = isFinished;
            this.todoLocation = todoLocation;
            this.todoGroupNum = todoGroupNum;
        }
    }

}
