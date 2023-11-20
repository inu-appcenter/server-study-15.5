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


    /*
    파라미터에 final 키워드를 붙이는 것을 어떨까? 피드백을 받았다.
    코드를 작성하며 final 키워드에 대해 많이 생각해보지 않았는데,
    음.. final은 어디에 붙이면 좋을까? 값을 바꾸지 않는 부분은 많은데 모든 곳에 붙이는 게 좋을까?
     */
    public List<TaskResponseDto> getAllByUserId(final Long userId) throws Exception {
        List<TaskResponseDto> taskResponseDtos;
        List<Task> taskList;

        try {
            taskList = taskRepository.findAllByUser_UserId(userId);
        } catch (Exception e) {
            throw new Exception("User를 찾을 수 없습니다.");
        }

        taskResponseDtos = taskList.stream().map(TaskResponseDto::from).collect(Collectors.toList());
        return taskResponseDtos;
    }

    public TaskResponseDto save(TaskRequestDto taskRequestDto) throws Exception {
        User selectedUser = userRepository.findById(taskRequestDto.getUserId())
                .orElseThrow(() -> new Exception("해당하는 사용자를 찾을 수 없습니다."));

        Task task = taskRequestDto.toEntity(selectedUser);
        Task savedTask = taskRepository.save(task);
        return savedTask.toResponseDto();
    }

    public void delete(Long taskId) throws Exception {
        Task selectedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("해당하는 task를 찾을 수 없습니다."));
        taskRepository.delete(selectedTask);
    }

    public void changeIsCompleted(Long taskId) throws Exception {
        Task selectedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("해당하는 task를 찾을 수 없습니다."));
        selectedTask.updateIsComleted();
        taskRepository.save(selectedTask);
    }


    private TaskResponseDto updateAndSaveTaskFromDto(Task task, TaskRequestDto taskRequestDto) {
        task.updateFromDto(taskRequestDto);
        return taskRepository.save(task)
                .toResponseDto();
    }

    private TaskResponseDto createAndSaveTaskFromDto(User taskOwner, TaskRequestDto taskRequestDto) {
        Task createdTask = taskRequestDto.toEntity(taskOwner);
        return taskRepository.save(createdTask)
                .toResponseDto();
    }

    public TaskResponseDto updateTask(TaskRequestDto taskRequestDto) throws Exception {
        Optional<Task> selectedTask = taskRepository.findById(taskRequestDto.getTaskId());
        if (selectedTask.isPresent()) {
            return updateAndSaveTaskFromDto(selectedTask.get(), taskRequestDto);
        }
        return save(taskRequestDto);
    }



}
