package com.example.TodoProject.entity;


import com.example.TodoProject.common.Time;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import static com.example.TodoProject.dto.Todo.TodoResponseDto.*;
import static com.example.TodoProject.dto.Todo.TodoRequestDto.*;

@Table(name = "todo_tb")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_num")
    private Long todoNum;

    @Column(name = "todo_title")
    private String todoTitle;

    @Column(nullable = false, name = "todo_description")
    private String todoDescription;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @Column(nullable = false, name = "todo_location")
    private String todoLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_num")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = true )
    @JoinColumn(name = "group_num")
    private TodoGroup todoGroup;

    @Builder
    public Todo(String todoTitle, String todoDescription, LocalDate startDate, LocalDate endDate, Boolean isFinished, String todoLocation, Client client, TodoGroup todoGroup) {
        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFinished = isFinished;
        this.todoLocation = todoLocation;
        this.client = client;
        this.todoGroup = todoGroup;
    }

    public void EditTodo(TodoGroup todoGroup, RequestTodoDto requestTodoDto){
        this.todoTitle = requestTodoDto.getTodoTitle();
        this.todoDescription = requestTodoDto.getTodoDescription();
        this.startDate = requestTodoDto.getStartDate();
        this.endDate = requestTodoDto.getEndDate();
        this.isFinished = requestTodoDto.getIsFinished();
        this.todoLocation = requestTodoDto.getTodoLocation();
        this.todoGroup = todoGroup;
    }

    public void EditTodosTodoGroup(){
        this.todoGroup = null;
    }

    public ResponseTodoDto toDto(){
        Long groupNum = (todoGroup != null) ? todoGroup.getGroupNum() : null;
        return ResponseTodoDto.builder()
                .todoNum(todoNum)
                .todoTitle(todoTitle)
                .todoDescription(todoDescription)
                .todoLocation(todoLocation)
                .todoGroupNum(groupNum)
                .endDate(endDate)
                .startDate(startDate)
                .build();
    }

}
