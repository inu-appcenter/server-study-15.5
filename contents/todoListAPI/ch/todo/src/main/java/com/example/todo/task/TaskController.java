package com.example.todo.task;

import com.example.todo.task.dto.TaskRequestDto;
import com.example.todo.task.dto.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByUserId(@PathVariable Long userId) throws Exception {
        List<TaskResponseDto> taskResponseDtos = taskService.getAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtos);
    }

    @PostMapping()
    public ResponseEntity<TaskResponseDto> postTask(@RequestBody TaskRequestDto taskRequestDto) throws Exception {
        TaskResponseDto taskResponseDto = taskService.save(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDto);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) throws Exception {
        taskService.delete(taskId);
    }

    @PatchMapping("/completed/{taskId}")
    public void changeTaskIsCompleted(@PathVariable Long taskId) throws Exception {
        taskService.changeIsCompleted(taskId);
    }

    @PutMapping()
    public ResponseEntity<TaskResponseDto> updateTask(@RequestBody TaskRequestDto taskRequestDto) throws Exception {
        TaskResponseDto taskResponseDto = taskService.updateTask(taskRequestDto);
        /*
        고민, HttpStatus를 NO_CONTENT와 CREATED로 나누고 싶은데 컨트롤러에서 어떻게 식별할 수 있을까
         */
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(taskResponseDto);
    }

}
