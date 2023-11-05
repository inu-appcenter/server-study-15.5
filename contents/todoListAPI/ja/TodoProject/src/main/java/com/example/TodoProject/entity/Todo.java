package com.example.TodoProject.entity;


import com.example.TodoProject.common.Time;
import javax.persistence.*;

import com.example.TodoProject.dto.RequestTodoDto;
import lombok.*;

import java.time.LocalDate;

@Table(name = "todo_tb")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public void EditTodo(RequestTodoDto requestTodoDto){
        this.todoTitle = requestTodoDto.getTodoTitle();
        this.todoDescription = requestTodoDto.getTodoDescription();
        this.startDate = requestTodoDto.getStartDate();
        this.endDate = requestTodoDto.getEndDate();
        this.isFinished = requestTodoDto.getIsFinished();
        this.todoLocation = requestTodoDto.getTodoLocation();
    }

}
