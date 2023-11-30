package com.example.TodoProject.dto.Todo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

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

        @Schema(example = "1")
        private Long todoGroupNum;


        @Builder
        public ResponseTodoDto(Long todoNum, String todoTitle, String todoDescription, LocalDate startDate, LocalDate endDate, Boolean isFinished, String todoLocation, Long todoGroupNum) {
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

    @Getter
    public static class ResponseTodoDeleteDto{
        @Schema(example = "2",description = "투두 번호")
        private long data;
    }

    @Getter
    public static class ResponseListDto{
        private List<ResponseTodoDto> data;
    }



}
