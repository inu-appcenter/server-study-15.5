package com.example.todo.task;

import com.example.todo.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "task_tb")
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private Boolean is_completed;
}
