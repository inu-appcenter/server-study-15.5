package com.example.TodoProject.entity;

import com.example.TodoProject.common.Time;
import javax.persistence.*;

import com.example.TodoProject.dto.RequestTodoDto;
import com.example.TodoProject.dto.RequestTodoGroupDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "todogroup_tb")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoGroup extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_num")
    private Long groupNum;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "is_important")
    private Boolean isImportant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_num")
    private Client client;

//    @Builder.Default
    @OneToMany(mappedBy = "todoGroup", cascade = CascadeType.ALL)
    private List<Todo> todo = new ArrayList<>();

    public void EditTodoGroup(RequestTodoGroupDto requestTodoGroupDto){
       this.groupName = requestTodoGroupDto.getGroupName();
       this.isImportant = requestTodoGroupDto.getIsImportant();
    }

        @Builder
        public TodoGroup(String groupName, Boolean isImportant, Client client){
            this.groupName = groupName;
            this.isImportant = isImportant;
            this.client = client;
        }
    }




