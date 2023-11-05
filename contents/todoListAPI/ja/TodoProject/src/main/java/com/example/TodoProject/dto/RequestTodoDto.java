package com.example.TodoProject.dto;


import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RequestTodoDto {

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
    public RequestTodoDto(String todoTitle, String todoDescription,LocalDate startDate, LocalDate endDate, Boolean isFinished, String todoLocation, Long todoGroupNum){
        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFinished =isFinished;
        this.todoLocation = todoLocation;
        this.todoGroupNum = todoGroupNum;
    }

    public Todo toEntity(Client client,TodoGroup todoGroup,RequestTodoDto requestTodoDto) {
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
