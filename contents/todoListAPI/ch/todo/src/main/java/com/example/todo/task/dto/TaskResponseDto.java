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

    @ApiModelProperty(value = "제목", notes = "할일 목록에 제목으로 나타남")
    private String title;

    @ApiModelProperty(value = "설명", notes = "Task에 대한 자세한 정보")
    private String description;

    @ApiModelProperty(value = "마감기한", notes = "Task 마감기한 지정")
    private LocalDateTime deadline;

    @Builder
    private TaskResponseDto(Long taskId, Long userId, String title, String description, LocalDateTime deadline) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    /*
    정적 팩토리 메서드를 통해 객체 내부 상태를 외부에 숨길 수 있다. (생성자: private)
     */
    public static TaskResponseDto from(Task task) {
        /*
        2023.11.14
        피드백: 클라이언트 측에서 이미 userId를 알고있는 상태로 요청을 보내는데
        ResponseDto에서 다시 userId를 반환해줄 필요가 있을까?

        => UserControlloer의 postUser와 같은 엔드포인트에는 당연히 ResponseDto(User)에
        userId가 포함되야 할 것이다. 다만, User가 소유하는 Task에 대한 요청은 모두
        해당 유저에 대한 정보를 이미 알고있다는 전제하에 들어오므로 TaskResponseDto에 UserId를 구태여
        포함하여 전송할 필요는 없어보인다.
         */
        return TaskResponseDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .build();
    }
}
