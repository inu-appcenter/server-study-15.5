package com.example.TodoProject.service;

import com.example.TodoProject.config.ex.NotFoundException;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import com.example.TodoProject.repository.ClientRepository;
import com.example.TodoProject.repository.TodoGroupRepository;
import com.example.TodoProject.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.TodoProject.dto.Todo.TodoRequestDto.*;
import static com.example.TodoProject.dto.Todo.TodoResponseDto.*;

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


    public void saveForTodoGroup(Long clientNum, RequestTodoDto requestTodoDto) {
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Long todoGroupNum = requestTodoDto.getTodoGroupNum();
        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                .orElseThrow(()->new NotFoundException("존재하지 않는 투두 그룹입니다."));

        Todo todo = requestTodoDto.toEntity(client, todoGroup, requestTodoDto);

        todoRepository.save(todo);
    }

    public void saveForNotTodoGroup(Long clientNum, RequestTodoDto requestTodoDto) {
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Todo todo = requestTodoDto.toEntity(client, null, requestTodoDto);

        todoRepository.save(todo);
    }

    public void editTodoForTodoGroup(Long todoNum, RequestTodoDto requestTodoDto){
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 투두입니다."));

        Long todoGroupNum =requestTodoDto.getTodoGroupNum();

        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                .orElseThrow(()->new NotFoundException("존재하지 않는 투두 그룹입니다."));

        todo.EditTodo(todoGroup, requestTodoDto);

        todoRepository.save(todo);
    }

    public void editTodoForNotTodoGroup(Long todoNum, RequestTodoDto requestTodoDto){
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 투두입니다."));

        todo.EditTodo(null, requestTodoDto);

        todoRepository.save(todo);
    }
    public void deleteTodo(Long todoNum){
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 투두입니다."));
        todoRepository.delete(todo);
    }

    public List<ResponseTodoDto> getUsersAllTodos(Long clientNum) {
        List<Todo> todos = todoRepository.findByClientClientNum(clientNum);
        List<ResponseTodoDto> todoDtos = todos.stream()
                .map(todo -> {
                    Long groupNum = (todo.getTodoGroup() != null) ? todo.getTodoGroup().getGroupNum() : null;
                    return new ResponseTodoDto(
                            todo.getTodoNum(),
                            todo.getTodoTitle(),
                            todo.getTodoDescription(),
                            todo.getStartDate(),
                            todo.getEndDate(),
                            todo.getIsFinished(),
                            todo.getTodoLocation(),
                            groupNum
                    );
                }).collect(Collectors.toList());
        return todoDtos;
    }


    public void toggleTodo(Long todoNum){
        Optional<Todo> todoIsFinished = todoRepository.findByTodoNum(todoNum);
        if(!todoIsFinished.isPresent()){
            throw new NotFoundException("투두가 존재하지 않습니다.");
        }
        Todo todo = todoIsFinished.get();
        todo.todoToggle(todo.getIsFinished() ? false : true);
        todoRepository.save(todo);

    }
}
