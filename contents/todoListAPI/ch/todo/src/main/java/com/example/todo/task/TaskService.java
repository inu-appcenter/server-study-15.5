package com.example.todo.task;

import com.example.todo.task.dto.TaskDto;
import com.example.todo.task.dto.TaskResponseDto;
import com.example.todo.user.User;
import com.example.todo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskResponseDto> getAllByUserId(Long userId) {
        List<TaskResponseDto> taskResponseDtos = new ArrayList<>();

        List<Task> taskList = taskRepository.findAllByUser_Id(userId);
        for (Task task : taskList) {
            taskResponseDtos.add(TaskResponseDto.builder()
                    .taskId(task.getTaskId())
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .deadline(task.getDeadline())
                    .userId(task.getUser().getUserId())
                    .build());
        }
        return taskResponseDtos;
    }

    public TaskResponseDto save(TaskDto taskDto) throws Exception {
        Optional<User> selectedUser = userRepository.findById(taskDto.getUserId());

        Task task;
        if (selectedUser.isPresent()) {
            task = Task.builder()
                    .title(taskDto.getTitle())
                    .description(taskDto.getDescription())
                    .deadline(taskDto.getDeadline())
                    .user(selectedUser.get())
                    .build();
        } else {
            throw new Exception();
        }

        Task savedTask = taskRepository.save(task);
        return TaskResponseDto.builder()
                .taskId(savedTask.getTaskId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .deadline(savedTask.getDeadline())
                .userId(savedTask.getUser().getUserId())
                .build();
    }

    public void delete(Long taskId) throws Exception {
        Optional<Task> selectedTask = taskRepository.findById(taskId);
        if (selectedTask.isEmpty()) {
            throw new Exception();
        }
        taskRepository.delete(selectedTask.get());
    }

    public void changeIsCompleted(Long taskId) throws Exception {
        Optional<Task> selectedTask = taskRepository.findById(taskId);
        if (selectedTask.isEmpty()) {
            throw new Exception();
        }

        Task task = selectedTask.get();
        task.setIsCompleted(!task.getIsCompleted());
        taskRepository.save(task);
    }

}
