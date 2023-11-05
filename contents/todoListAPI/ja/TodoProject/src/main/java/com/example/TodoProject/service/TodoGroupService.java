package com.example.TodoProject.service;


import com.example.TodoProject.dto.RequestTodoGroupDto;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.TodoGroup;
import com.example.TodoProject.repository.ClientRepository;
import com.example.TodoProject.repository.TodoGroupRepository;
import com.example.TodoProject.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoGroupService {
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
                .orElseThrow(() -> new RuntimeException());
        TodoGroup todoGroup = requestTodoGroupDto.toEntity(client, requestTodoGroupDto);
        todoGroupRepository.save(todoGroup);
    }

    public void editTodoGroup(Long todoGroupNum, RequestTodoGroupDto requestTodoGroupDto){
        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                .orElseThrow(() -> new RuntimeException());
        todoGroup.EditTodoGroup(requestTodoGroupDto);
        todoGroupRepository.save(todoGroup);
    }
}
