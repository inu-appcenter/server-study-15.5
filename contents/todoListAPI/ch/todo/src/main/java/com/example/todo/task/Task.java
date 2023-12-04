package com.example.todo.task;

import com.example.todo.task.dto.TaskRequestDto;
import com.example.todo.task.dto.TaskResponseDto;
import com.example.todo.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "task_tb")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "is_completed")
    private Boolean isCompleted;

    /*
    AllArgsConstructor는 매개변수의 위치를 보장하지 못 한다. -> 찾을 수 없는 오류의 원인.
    Builder와 NoArgsConstructor를 Class 레벨에 두고 컴파일 시 오류발생.
    -> AllArgsConstructor를 명시해서 해결할 수 있지만, 위에서 말한 것과 같이 AllArgs..는 위험이 많다.
    => 생성자를 명시적으로 만들고, 생성자에 Builder 어노테이션을 붙이면 노출하지 않아도 되는 필드를
    숨기면서, AllArgs의 문제없이 안정적으로 사용할 수 있다.
     */
    @Builder
    public Task(User user, String title, String description, LocalDateTime deadline) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    /*
    2023.11.06 수정, @setter 대신 행위를 나타내는 메서드 사용.
    도메인 별 패키지 구분. -> service에서만 사용할 메서드이므로 protected
     */
    protected void updateIsComleted() {
        this.isCompleted = !this.isCompleted;
    }

    public TaskResponseDto toResponseDto() {
        return TaskResponseDto.builder()
                .taskId(this.taskId)
                .title(this.title)
                .description(this.description)
                .deadline(this.deadline)
                .userId(this.user.getUserId())
                .build();
    }

    public Task updateFromDto(TaskRequestDto taskRequestDto) {
        this.title = taskRequestDto.getTitle();
        this.description = taskRequestDto.getDescription();
        this.deadline = taskRequestDto.getDeadline();
        return this;
    }

}
