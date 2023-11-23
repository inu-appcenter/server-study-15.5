package com.example.TodoProject.service;


import com.example.TodoProject.config.ex.NotFoundException;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import com.example.TodoProject.repository.ClientRepository;
import com.example.TodoProject.repository.TodoGroupRepository;
import com.example.TodoProject.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.TodoProject.dto.TodoGroup.TodoGroupRequestDto.*;
import static com.example.TodoProject.dto.TodoGroup.TodoGroupResponseDto.*;
import static com.example.TodoProject.dto.Todo.TodoResponseDto.*;
import static com.example.TodoProject.dto.Todo.TodoRequestDto.*;


@Service
@Slf4j
public class TodoGroupService {

    //@PersistenceContext
    private final TodoRepository todoRepository;

    private final ClientRepository clientRepository;

    private final TodoGroupRepository todoGroupRepository;

    @Autowired
    public TodoGroupService(TodoRepository todoRepository, ClientRepository clientRepository, TodoGroupRepository todoGroupRepository){
        this.clientRepository = clientRepository;
        this.todoRepository = todoRepository;
        this.todoGroupRepository = todoGroupRepository;
    }
    public void saveTodoGroup(Long clientNum, RequestTodoGroupDto requestTodoGroupDto){
        log.info("[saveTodoGroup] 투두 그룹 생성중");
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
        todoGroupRepository.save(requestTodoGroupDto.toEntity(client, requestTodoGroupDto));
        log.info("[saveTodoGroup] 투두 그룹 생성완료");
    }

    public void editTodoGroup(Long todoGroupNum, RequestTodoGroupDto requestTodoGroupDto){
        log.info("[editTodoGroup] 투두 그룹 수정중");
        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 투두입니다."));
        todoGroup.EditTodoGroup(requestTodoGroupDto);
        todoGroupRepository.save(todoGroup);
        log.info("[editTodoGroup] 투두 그룹 수정완료");
    }

    public List<ResponseTodoGroupDto> getAllTodoGroup(Long clientNum){
        log.info("[getAllTodoGroup] 유저가 가지고 있는 전체 투두 소환");
        List<TodoGroup> todoGroups = todoGroupRepository.findByClientClientNum(clientNum);
        List<ResponseTodoGroupDto> todoGroupDtos = todoGroups.stream()
                .map(todoGroup-> new ResponseTodoGroupDto(
                        todoGroup.getGroupNum(),
                        todoGroup.getGroupName(),
                        todoGroup.getIsImportant()
                ))
                .collect(Collectors.toList());
        return todoGroupDtos;
    }

    @Transactional(readOnly = true)
    public List<TodoListDto> getAllTodoForTodoGroup(Long clientNum){
        log.info("[getAllTodoForTodoGroup] 유저가 가지고 있는 투두 그룹의 전체 투두 소환");
        List<TodoGroup> todoGroups = todoGroupRepository.findByClientClientNum(clientNum);

        List<TodoListDto> todoListDtos = todoGroups.stream()
                .map( todoGroup -> new TodoListDto(
                       todoGroup.getGroupName(),
                        todoGroup.getIsImportant(),
                        todoGroup.getTodo().stream().map(
                                todo->todo.toDto()
                                ).collect(Collectors.toList())
                )).collect(Collectors.toList());
        return todoListDtos;
    }



}
