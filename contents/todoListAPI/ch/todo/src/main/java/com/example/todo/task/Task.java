package com.example.todo.task;

import com.example.todo.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "task_tb")
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String description;

    private LocalDateTime deadline;

    @Setter
    @Column(name = "is_completed")
    private Boolean isCompleted;
}
