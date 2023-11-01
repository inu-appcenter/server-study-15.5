package com.example.TodoProject.entity;


import com.example.TodoProject.common.Time;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "Todo_tb")
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
    @JoinColumn(name = "clientNum")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupNum")
    private TodoGroup todoGroup;

}
