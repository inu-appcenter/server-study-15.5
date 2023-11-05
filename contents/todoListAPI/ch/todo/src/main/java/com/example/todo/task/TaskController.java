package com.example.todo.task;

import com.example.todo.task.dto.TaskDto;
import com.example.todo.task.dto.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tasks")
@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDto> taskResponseDtos = taskService.getAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtos);
    }

    @PostMapping()
    public ResponseEntity<TaskResponseDto> postTask(@RequestBody TaskDto taskDto) throws Exception {
        TaskResponseDto taskResponseDto = taskService.save(taskDto);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDto);
    }

    @DeleteMapping("/{taskId}")
    public void deletetask(@PathVariable Long taskId) throws Exception {
        taskService.delete(taskId);
    }

    @PatchMapping("/iscompleted/{taskId}")
    public void postTask(@PathVariable Long taskId) throws Exception {
        taskService.changeIsCompleted(taskId);
    }
}
