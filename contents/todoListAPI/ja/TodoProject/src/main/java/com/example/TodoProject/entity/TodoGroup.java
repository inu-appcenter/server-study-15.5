package com.example.TodoProject.entity;

import com.example.TodoProject.common.Time;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TodoGroup_tb")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoGroup extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupNum;

    private String groupName;

    private Boolean isImportant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientNum")
    private Client client;

    @Builder.Default
    @OneToMany(mappedBy = "todoGroup", cascade = CascadeType.ALL)
    private List<Todo> todoList = new ArrayList<>();


}
