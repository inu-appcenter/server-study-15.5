package com.example.TodoProject.service;

import com.example.TodoProject.config.ex.NotFoundElementException;
import com.example.TodoProject.config.ex.NotRightThisObject;
import com.example.TodoProject.config.security.JwtProvider;
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
import java.util.stream.Collectors;

import static com.example.TodoProject.dto.Todo.TodoRequestDto.*;
import static com.example.TodoProject.dto.Todo.TodoResponseDto.*;

@Service
@Slf4j
public class TodoService {


    private final TodoRepository todoRepository;

    private final ClientRepository clientRepository;

    private final TodoGroupRepository todoGroupRepository;

    private final JwtProvider jwtProvider;

    @Autowired
    public TodoService(JwtProvider jwtProvider, TodoRepository todoRepository, ClientRepository clientRepository, TodoGroupRepository todoGroupRepository){
        this.jwtProvider = jwtProvider;
        this.clientRepository = clientRepository;
        this.todoRepository = todoRepository;
        this.todoGroupRepository = todoGroupRepository;
    }


    public void createTodo(String token, RequestTodoDto requestTodoDto) {
        log.info("[createTodo] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[createTodo] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("[saveForTodoGroup]투두 저장중. 사용자 id: {} , 투두 제목: {}",clientNum ,requestTodoDto.getTodoTitle());
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));

        TodoGroup todoGroup;

        if(requestTodoDto.getTodoGroupNum() != null){
            Long todoGroupNum = requestTodoDto.getTodoGroupNum();
            todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                    .orElseThrow(()->new NotFoundElementException("존재하지 않는 투두 그룹입니다."));
        }
        else{
            todoGroup = null;
        }

        Todo todo = requestTodoDto.toEntity(client, todoGroup, requestTodoDto);
        log.info("[saveForTodoGroup]투두 저장완료. 사용자 id: {} , 투두 제목: {}",clientNum ,requestTodoDto.getTodoTitle());
        todoRepository.save(todo);
    }


    public void editTodo(String token ,Long todoNum, RequestTodoDto requestTodoDto){
        log.info("[editTodo] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[editTodo] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("[editTodo] 투두 소유주 확인");
        Client client = clientRepository.findByClientNum(clientNum)
                        .orElseThrow(()-> new NotFoundElementException("존재하지 않는 유저입니다."));

        if(client.getClientNum() != clientNum){
            throw new NotRightThisObject("투두의 소유주가 아닙니다.");
        }

        log.info("[editTodo] 투두 수정 시작. todo id: {}", todoNum);
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 투두입니다."));

        TodoGroup todoGroup;

        if(requestTodoDto.getTodoGroupNum() != null){
            Long todoGroupNum =requestTodoDto.getTodoGroupNum();
            todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                    .orElseThrow(()->new NotFoundElementException("존재하지 않는 투두 그룹입니다."));
        }
        else{
            todoGroup = null;
        }
        todo.EditTodo(todoGroup, requestTodoDto);
        log.info("[editTodo] 투두 수정완료. todo id: {}", todoNum);
        todoRepository.save(todo);
    }

    public void editTodoGroup(Todo todo){
        todo.EditTodosTodoGroup();
        todoRepository.save(todo);
    }

    public void deleteTodo(String token, Long todoNum){
        log.info("[deleteTodo] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[deleteTodo] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("[deleteTodo] 투두 소유주 확인");
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(()-> new NotFoundElementException("존재하지 않는 유저입니다."));

        if(client.getClientNum() != clientNum){
            throw new NotRightThisObject("투두의 소유주가 아닙니다.");
        }
        log.info("[deleteTodo] 투두 삭제하기. todo id: {}", todoNum);
        Todo todo = todoRepository.findByTodoNum(todoNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 투두입니다."));
        todoRepository.delete(todo);
        log.info("[deleteTodo] 투두 삭제완료. todo id: {}", todoNum);
    }

    public List<ResponseTodoDto> getUsersSearchTodos(String token, String keyword) {
        log.info("[getUsersSearchTodos] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[getUsersSearchTodos] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("[getUsersAllTodos] 그 유저의 투두 전체소환. client Num: {}", clientNum);

        List<Todo> todos = todoRepository.findAllByClientClientNumAndTodoTitleContains(clientNum, keyword);

        List<ResponseTodoDto> todoDtos = todos.stream()
                .map(todo -> todo.toDto()
                ).collect(Collectors.toList());
        return todoDtos;
    }

    @Transactional(readOnly = true)
    public List<ResponseTodoDto> getAllTodosForNotTodoGroup(String token){
        log.info("[getAllTodosForNotTodoGroup] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[getAllTodosForNotTodoGroup] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("[getAllTodosForNotTodoGroup] 투두 그룹이 없는 투두 전체 소환. clientNum id: {}", clientNum);
        clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));

        List<Todo> todos = todoRepository.findByClientClientNumAndTodoGroup(clientNum,null);

        List<ResponseTodoDto> notTodoGroup = todos.stream()
                .map( Todo :: toDto)
                .collect(Collectors.toList());

        return notTodoGroup;
    }

}
