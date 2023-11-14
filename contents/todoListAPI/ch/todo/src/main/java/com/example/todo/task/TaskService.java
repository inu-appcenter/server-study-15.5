package com.example.todo.task;

import com.example.todo.task.dto.TaskRequestDto;
import com.example.todo.task.dto.TaskResponseDto;
import com.example.todo.user.User;
import com.example.todo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        /*
        Optional<User> selectedUser = userRepository.findById(taskRequestDto.getUserId());
        selectedUser.orElseThrow(() -> new Exception("해당하는 사용자를 찾지 못 함."));
         */
        // 피드백: 가독성을 위해 한번에 작성
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


    public TaskResponseDto updateTask(TaskRequestDto taskRequestDto) throws Exception {
        /*
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
            puttedTask = taskRequestDto.toEntity(taskOwner.get());
            savedTask = taskRepository.save(puttedTask);
        }
         */

        /*
        일차적으로 puutedTask를 들어내고 findBy~와 orElseThrow를 한 번에 수행하도록 변경했다.
        조금 더 간결하고 직관적인 코드가 되었다.
         */
        Task selectedTask;
        Task savedTask;
        try {
             selectedTask = taskRepository.findById(taskRequestDto.getTaskId())
                    .orElseThrow(() -> new Exception("해당하는 Task 정보를 찾을 수 없음"));
            selectedTask.updateFromDto(taskRequestDto);
            savedTask = taskRepository.save(selectedTask);
        } catch (Exception taskNotFoundException) {
            User taskOwner = userRepository.findById(taskRequestDto.getUserId())
                    .orElseThrow(() -> new Exception("해당 Task와 User의 정보를 찾을 수 없음"));
            selectedTask = taskRequestDto.toEntity(taskOwner);
            savedTask = taskRepository.save(selectedTask);
        }

        return savedTask.toResponseDto();
    }

/*
    public TaskResponseDto updateTask(TaskRequestDto taskRequestDto) throws Exception {
        Task savedTask;
        Task puttedTask;
        Optional<Task> selectedTask = taskRepository.findById(taskRequestDto.getTaskId());

        if (selectedTask.isPresent()) {
            puttedTask = selectedTask.get();
            puttedTask.updateFromDto(taskRequestDto);
            savedTask = taskRepository.save(puttedTask);
            return savedTask.toResponseDto();
        }

        User taskOwner = userRepository.findById(taskRequestDto.getUserId())
                .orElseThrow(() -> new Exception("해당 Task와 User의 정보를 찾을 수 없음"));
        puttedTask = taskRequestDto.toEntity(taskOwner);
        savedTask = taskRepository.save(puttedTask);
        return savedTask.toResponseDto();
    }

 */

/*
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
        Task savedTask;
        Task puttedTask;
        Optional<Task> selectedTask = taskRepository.findById(taskRequestDto.getTaskId());

        if (selectedTask.isPresent()) {
            return updateAndSaveTaskFromDto(selectedTask.get(), taskRequestDto);
        }

        User taskOwner = userRepository.findById(taskRequestDto.getUserId())
                .orElseThrow(() -> new Exception("해당 Task와 User의 정보를 찾을 수 없음"));
        return createAndSaveTaskFromDto(taskOwner, taskRequestDto);
    }

 */

}
