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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.TodoProject.dto.Todo.TodoRequestDto.*;
import static com.example.TodoProject.dto.Todo.TodoResponseDto.*;

@Service
@Slf4j
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
        log.info("[saveForTodoGroup]투두 그룹이 있는 투두 저장중. 사용자 id: {} , 투두 제목: {}",clientNum ,requestTodoDto.getTodoTitle());
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Long todoGroupNum = requestTodoDto.getTodoGroupNum();
        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                .orElseThrow(()->new NotFoundException("존재하지 않는 투두 그룹입니다."));

        Todo todo = requestTodoDto.toEntity(client, todoGroup, requestTodoDto);
        log.info("[saveForTodoGroup]투두 그룹이 있는 투두 저장완료. 사용자 id: {} , 투두 제목: {}",clientNum ,requestTodoDto.getTodoTitle());
        todoRepository.save(todo);
    }

    public void saveForNotTodoGroup(Long clientNum, RequestTodoDto requestTodoDto) {
        log.info("[saveForNotTodoGroup]투두 그룹이 있는 투두 저장중. 사용자 id: {} , 투두 제목: {}",clientNum ,requestTodoDto.getTodoTitle());
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Todo todo = requestTodoDto.toEntity(client, null, requestTodoDto);
        log.info("[saveForNotTodoGroup]투두 그룹이 있는 투두 저장완료. 사용자 id: {} , 투두 제목: {}",clientNum ,requestTodoDto.getTodoTitle());
        todoRepository.save(todo);
    }

    public void editTodoForTodoGroup(Long todoNum, RequestTodoDto requestTodoDto){
        log.info("[editTodoForTodoGroup] 투두 그룹이 있는 투두 수정. todo id: {}", todoNum);
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 투두입니다."));

        Long todoGroupNum =requestTodoDto.getTodoGroupNum();

        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                .orElseThrow(()->new NotFoundException("존재하지 않는 투두 그룹입니다."));

        todo.EditTodo(todoGroup, requestTodoDto);
        log.info("[editTodoForTodoGroup] 투두 그룹이 있는 투두 수정완료. todo id: {}", todoNum);
        todoRepository.save(todo);
    }

    public void editTodoForNotTodoGroup(Long todoNum, RequestTodoDto requestTodoDto){
        log.info("[editTodoForNotTodoGroup] 투두 그룹이 없는 투두 수정. todo id: {}", todoNum);
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 투두입니다."));

        todo.EditTodo(null, requestTodoDto);
        log.info("[editTodoForNotTodoGroup] 투두 그룹이 없는 투두 수정완료. todo id: {}", todoNum);
        todoRepository.save(todo);
    }
    public void deleteTodo(Long todoNum){
        log.info("[deleteTodo] 투두 삭제하기. todo id: {}", todoNum);
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 투두입니다."));
        todoRepository.delete(todo);
        log.info("[deleteTodo] 투두 삭제완료. todo id: {}", todoNum);
    }

    public List<ResponseTodoDto> getUsersAllTodos(Long clientNum) {
        log.info("[getUsersAllTodos] 그 유저의 투두 전체소환. client Num: {}", clientNum);
        List<Todo> todos = todoRepository.findByClientClientNum(clientNum);
        List<ResponseTodoDto> todoDtos = todos.stream()
                .map(todo -> todo.toDto()
                ).collect(Collectors.toList());
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

    @Transactional(readOnly = true)
    public List<ResponseTodoDto> getAllTodosForNotTodoGroup(Long clientNum){
        log.info("[getAllTodosForNotTodoGroup] 투두 그룹이 없는 투두 전체 소환. clientNum id: {}", clientNum);
        List<Todo> todos = todoRepository.findByClientClientNumAndTodoGroup(clientNum,null);

        List<ResponseTodoDto> notTodoGroup = todos.stream()
                .map( Todo :: toDto
                ).collect(Collectors.toList());

        return notTodoGroup;
    }

}
