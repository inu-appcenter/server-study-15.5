package com.example.todo.task;

import com.example.todo.common.ValidationGroup;
import com.example.todo.task.dto.TaskRequestDto;
import com.example.todo.task.dto.TaskResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Task"})
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{userId}")
    @ApiOperation(value = "User의 모든 Task 조회 api", notes = "특정 유저의 모든 Task 조회")
    public ResponseEntity<List<TaskResponseDto>> getTasksByUserId(
            @ApiParam(value = "User 식별자", required = true) @PathVariable Long userId) throws Exception {
        List<TaskResponseDto> taskResponseDtos = taskService.getAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtos);
    }

    @PostMapping()
    @ApiOperation(value = "새 Task 등록 api", notes = "새 Task 등록")
    public ResponseEntity<TaskResponseDto> postTask(
            @Validated @RequestBody TaskRequestDto taskRequestDto) throws Exception {
        TaskResponseDto taskResponseDto = taskService.save(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDto);
    }

    @DeleteMapping("/{taskId}")
    @ApiOperation(value = "기존 Task 삭제 api", notes = "Task 삭제")
    public void deleteTask(
            @ApiParam(value = "Task(할일) 식별자", required = true) @PathVariable Long taskId) throws Exception {
        taskService.delete(taskId);
    }

    @PatchMapping("/completed/{taskId}")
    @ApiOperation(value = "Task 완료 상태 변경 api", notes = "isCompleted 토글")
    public void changeTaskIsCompleted(
            @ApiParam(value = "Task(할일) 식별자", required = true) @PathVariable Long taskId) throws Exception {
        taskService.changeIsCompleted(taskId);
    }

    @PutMapping()
    @ApiOperation(value = "Task 수정 api", notes = "Task 수정")
    public ResponseEntity<TaskResponseDto> updateTask(
            @Validated(ValidationGroup.RUDValidationGroup.class)
            @RequestBody TaskRequestDto taskRequestDto) throws Exception {
        TaskResponseDto taskResponseDto = taskService.updateTask(taskRequestDto);
        return  ResponseEntity.status(HttpStatus.OK).body(taskResponseDto);
    }

}
