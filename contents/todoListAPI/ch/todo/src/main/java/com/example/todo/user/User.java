package com.example.todo.user;

import com.example.todo.task.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.matcher.FilterableList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user_tb")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, name = "login_id")
    private String loginId;

    private String password;

    private String name;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> Tasks = new ArrayList<>();
}
