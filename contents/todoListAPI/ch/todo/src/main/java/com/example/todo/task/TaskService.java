package com.example.todo.task;

import com.example.todo.task.dto.TaskRequestDto;
import com.example.todo.task.dto.TaskResponseDto;
import com.example.todo.user.User;
import com.example.todo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskResponseDto> getAllByUserId(Long userId) throws Exception {
        List<TaskResponseDto> taskResponseDtos;
        List<Task> taskList;

        try {
            taskList = taskRepository.findAllByUser_UserId(userId);
        } catch (Exception e) {
            throw new Exception("User를 찾을 수 없습니다.");
        }

        taskResponseDtos = taskList.stream().map(TaskResponseDto::new).collect(Collectors.toList());
        return taskResponseDtos;
    }

    public TaskResponseDto save(TaskRequestDto taskRequestDto) throws Exception {
        Optional<User> selectedUser = userRepository.findById(taskRequestDto.getUserId());
        selectedUser.orElseThrow(() -> new Exception("해당하는 사용자를 찾지 못 함."));

        Task task = TaskRequestDto.toEntity(taskRequestDto, selectedUser.get());
        Task savedTask = taskRepository.save(task);
        return Task.toResponseDto(savedTask);
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
        selectedTask.orElseThrow(() -> new Exception("해당하는 task를 찾을 수 없습니다."));

        Task task = selectedTask.get();
        task.updateIsComleted();
        taskRepository.save(task);
    }

    public TaskResponseDto updateTask(TaskRequestDto taskRequestDto) throws Exception {
        Optional<Task> selectedTask = taskRepository.findById(taskRequestDto.getTaskId());

        Task savedTask;
        Task puttedTask;
        try {
            selectedTask.orElseThrow(() -> new Exception("해당하는 Task 정보를 찾을 수 없음"));
            puttedTask = selectedTask.get();
            puttedTask.updateFromDto(taskRequestDto);
            savedTask = taskRepository.save(puttedTask);
        } catch (Exception taskNotFoundException) {
            Optional<User> taskOwner = userRepository.findById(taskRequestDto.getUserId());
            taskOwner.orElseThrow(() -> new Exception("해당 Task와 User의 정보를 찾을 수 없음"));
            puttedTask = TaskRequestDto.toEntity(taskRequestDto, taskOwner.get());
            savedTask = taskRepository.save(puttedTask);
        }
        return Task.toResponseDto(savedTask);
    }

}
