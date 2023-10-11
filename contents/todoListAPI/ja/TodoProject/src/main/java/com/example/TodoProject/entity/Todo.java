package com.example.TodoProject.entity;


import com.example.TodoProject.common.Time;
import jakarta.persistence.*;
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
    private Long todoNum;

    private String todoTitle;

    @Column(nullable = false)
    private String todoDescription;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isFinished;

    @Column(nullable = false)
    private String todoPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientNum")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupNum")
    private TodoGroup todoGroup;

}
