package com.example.TodoProject.service;


import com.example.TodoProject.config.ex.NotFoundException;
import com.example.TodoProject.dto.AllDto;
import com.example.TodoProject.dto.RequestTodoDto;
import com.example.TodoProject.dto.RequestTodoGroupDto;
import com.example.TodoProject.dto.ResponseTodoDto;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import com.example.TodoProject.repository.ClientRepository;
import com.example.TodoProject.repository.TodoGroupRepository;
import com.example.TodoProject.repository.TodoRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoGroupService {

    @PersistenceContext
    private EntityManager entityManager;
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
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
        todoGroupRepository.save(requestTodoGroupDto.toEntity(client, requestTodoGroupDto));
    }

    public void editTodoGroup(Long todoGroupNum, RequestTodoGroupDto requestTodoGroupDto){
        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 투두입니다."));
        todoGroup.EditTodoGroup(requestTodoGroupDto);
        todoGroupRepository.save(todoGroup);
    }

    public List<RequestTodoGroupDto> getAllTodoGroup(Long clientNum){
        List<TodoGroup> todoGroups = todoGroupRepository.findByClientClientNum(clientNum);
        List<RequestTodoGroupDto> todoGroupDtos = todoGroups.stream()
                .map(todoGroup-> new RequestTodoGroupDto(
                        todoGroup.getGroupName(),
                        todoGroup.getIsImportant()
                ))
                .collect(Collectors.toList());
        return todoGroupDtos;
    }

    @Transactional
    public List<AllDto> getAllTodoGroupsTodo(Long clientNum){
        List<TodoGroup> todoGroups = todoGroupRepository.findByClientClientNum(clientNum);

        List<AllDto> allDtos = todoGroups.stream()
                .map( todoGroup -> new AllDto(
                       todoGroup.getGroupName(),
                        todoGroup.getIsImportant(),
                        todoGroup.getTodo().stream().map(
                                todo->new ResponseTodoDto(
                                        todo.getTodoNum(),
                                        todo.getTodoTitle(),
                                        todo.getTodoDescription(),
                                        todo.getStartDate(),
                                        todo.getEndDate(),
                                        todo.getIsFinished(),
                                        todo.getTodoLocation(),
                                        todoGroup.getGroupNum()
                                )).collect(Collectors.toList())

                ))
                .collect(Collectors.toList());
        return allDtos;
    }

}
