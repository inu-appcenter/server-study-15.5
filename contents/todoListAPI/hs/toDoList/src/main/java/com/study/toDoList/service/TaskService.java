package com.study.toDoList.service;

import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import com.study.toDoList.dto.TaskListResponseDto;
import com.study.toDoList.dto.TaskResponseDto;
import com.study.toDoList.dto.TaskSaveDto;
import com.study.toDoList.dto.TaskUpdateDto;
import com.study.toDoList.repositoy.MemberRepository;
import com.study.toDoList.repositoy.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Long id,TaskSaveDto taskSaveDto){
        Optional<Member> member = memberRepository.findById(id);
        return taskRepository.save(Task.builder().member(member.get()).title(taskSaveDto.getTitle()).description(taskSaveDto.getDescription()).endDate(taskSaveDto.getEndDate()).build()).getId();
    }

    @Transactional
    public Long update(Long id, TaskUpdateDto taskUpdateDto){
        Optional<Task> task =taskRepository.findById(id);
        task.get().update(taskUpdateDto.getTitle(),taskUpdateDto.getDescription(),taskUpdateDto.getEndDate(),taskUpdateDto.getIsFinished());
        return id;
    }

    @Transactional
    public void delete(Long id){
        Optional<Task> task = taskRepository.findById(id);
        taskRepository.delete(task.get());
    }

    @Transactional(readOnly = true)
    public List<TaskListResponseDto> getAllTask(Long id){
        Optional<Member> member = memberRepository.findById(id);
        return taskRepository.findAllByMember(member.get()).stream().map(TaskListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTask(Long id){
        Optional<Task> task = taskRepository.findById(id);
        return new TaskResponseDto(task.get());
    }

}
