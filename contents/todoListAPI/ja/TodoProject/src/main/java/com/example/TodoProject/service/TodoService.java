package com.example.TodoProject.service;

import com.example.TodoProject.dto.RequestTodoDto;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import com.example.TodoProject.repository.ClientRepository;
import com.example.TodoProject.repository.TodoGroupRepository;
import com.example.TodoProject.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {


    private final TodoRepository todoRepository;

    private final ClientRepository clientRepository;

    private final TodoGroupRepository todoGroupRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository, ClientRepository clientRepository, TodoGroupRepository todoGroupRepository){
        this.clientRepository = clientRepository;
        this.todoRepository = todoRepository;
        this.todoGroupRepository = todoGroupRepository;
    }
    public void save(Long clientNum, RequestTodoDto requestTodoDto) {
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new RuntimeException());

        Long todoGroupNum = requestTodoDto.getTodoGroupNum();
        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum).orElse(null);

        Todo todo = Todo.builder()
                .client(client)
                .todoTitle(requestTodoDto.getTodoTitle())
                .todoDescription(requestTodoDto.getTodoDescription())
                .startDate(requestTodoDto.getStartDate())
                .endDate(requestTodoDto.getEndDate())
                .isFinished(requestTodoDto.getIsFinished())
                .todoLocation(requestTodoDto.getTodoLocation())
                .todoGroup(todoGroup)
                .build();

        todoRepository.save(todo);
    }

    public void editTodo(Long todoNum, RequestTodoDto requestTodoDto){
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new RuntimeException());
        todo.EditTodo(requestTodoDto);
        todoRepository.save(todo);
    }

    public void deleteTodo(Long todoNum){
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new RuntimeException());
        todoRepository.delete(todo);
    }
    public List<RequestTodoDto> getUsersAllTodos(Long clientNum) {
        List<Todo> todos = todoRepository.findByClientClientNum(clientNum);
        List<RequestTodoDto> todoDtos = new ArrayList<>();
        for (Todo todo : todos) {
            RequestTodoDto requestTodoDto = new RequestTodoDto(todo.getTodoTitle(), todo.getTodoDescription(), todo.getStartDate(), todo.getEndDate(), todo.getIsFinished(), todo.getTodoLocation(), todo.getTodoNum());
            todoDtos.add(requestTodoDto);
        }
        return todoDtos;
    }



}
