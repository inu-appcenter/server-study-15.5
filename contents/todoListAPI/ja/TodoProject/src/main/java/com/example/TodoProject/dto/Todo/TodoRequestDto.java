package com.example.TodoProject.dto.Todo;

import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TodoRequestDto {

    @Getter
    public static class RequestTodoDto {

        @Schema(description = "투두의 제목이다. ",example = "도서관 가서 라면먹기")
        @NotBlank(message = "투두 제목은 필수 요소입니다.")
        private String todoTitle;

        @Schema(description ="투두에 대한 설명이다. 빈칸가능." , example = "도서관에 가서 신라면에 꿀단지를 먹을 것이다.")
        private String todoDescription;

        @NotNull(message = "투두 시작 날짜를 입력해주세요.")
        @Schema(description = "투두 시작 날짜")
        private LocalDate startDate;

        @NotNull(message = "투두의 마지막 날짜를 입력해주세요")
        @Schema(description = "투두 마지막 날짜")
        private LocalDate endDate;

        @NotNull(message = "isFinished가 비었습니다.")
        @Schema(description = "투두가 완수되었는가 아닌가에 대한 Boolean값", example = "False")
        private Boolean isFinished;


        @Schema(description = "투두가 가리키는 장소",example = "도서관 지하열람실 이마트24")
        private String todoLocation;


        @Schema(description = "투두가 속한 그룹명. 그룹에 속하지 않은 투두면 null값",example = "1")
        private Long todoGroupNum;


        public Todo toEntity(Client client, TodoGroup todoGroup, RequestTodoDto requestTodoDto) {
            return Todo.builder()
                    .client(client)
                    .todoGroup(todoGroup)
                    .todoTitle(getTodoTitle())
                    .todoDescription(getTodoDescription())
                    .startDate(getStartDate())
                    .endDate(getEndDate())
                    .isFinished(getIsFinished())
                    .todoLocation(getTodoLocation())
                    .build();
        }
    }
}
