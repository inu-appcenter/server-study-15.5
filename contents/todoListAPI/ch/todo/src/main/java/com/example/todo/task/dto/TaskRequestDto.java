package com.example.todo.task.dto;

import com.example.todo.common.ValidationGroup;
import com.example.todo.task.Task;
import com.example.todo.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ApiModel("Task(할일) 요청 DTO")
@Getter
public class TaskRequestDto {

    @Positive(groups = {ValidationGroup.RUDValidationGroup.class})
    @ApiModelProperty(value = "Task 식별자", notes = "Long 타입의 양수값")
    private Long taskId;

    @Positive
    @ApiModelProperty(value = "User 식별자", notes = "Long 타입의 양수값")
    private Long userId;

    @Size(max=50)
    @NotBlank
    @ApiModelProperty(value = "제목", notes = "할일 목록에 제목으로 나타남")
    private String title;

    @Size(max = 255)
    @ApiModelProperty(value = "설명", notes = "Task에 대한 자세한 정보")
    private String description;

    @ApiModelProperty(value = "마감기한", notes = "Task 마감기한 지정")
    private LocalDateTime deadline;

    public Task toEntity(User user) {
        return Task.builder()
                .title(this.title)
                .description(this.description)
                .deadline(this.deadline)
                .user(user)
                .build();
    }
}
