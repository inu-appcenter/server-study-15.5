package com.example.todo.task.dto;

import com.example.todo.task.Task;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel("Task(할일) 응답 DTO")
@Getter
public class TaskResponseDto {

    @ApiModelProperty(value = "Task 식별자", notes = "Long 타입의 정수값")
    private Long taskId;

    @ApiModelProperty(value = "User 식별자", notes = "Long 타입의 정수값")
    private Long userId;

    @ApiModelProperty(value = "제목", notes = "할일 목록에 제목으로 나타남")
    private String title;

    @ApiModelProperty(value = "설명", notes = "Task에 대한 자세한 정보")
    private String description;

    @ApiModelProperty(value = "마감기한", notes = "Task 마감기한 지정")
    private LocalDateTime deadline;

    @Builder
    public TaskResponseDto(Long taskId, Long userId, String title, String description, LocalDateTime deadline) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public TaskResponseDto(Task task) {
        this.taskId = task.getTaskId();

        // 이렇게되면 ReponseDTO에 필요없는 User 정보가 포함되지는 않나?
        // 로딩시점을 Lazy로 설정해놓고도, DTO를 만들기 위해 User 엔티티를 조회하게 되는거 아닌가.
        this.userId = task.getUser().getUserId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.deadline = task.getDeadline();
    }
}
