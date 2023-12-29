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

import java.util.*;
import java.util.stream.Collectors;

import static com.example.TodoProject.dto.TodoGroup.TodoGroupRequestDto.*;
import static com.example.TodoProject.dto.TodoGroup.TodoGroupResponseDto.*;


@Service
@Slf4j
public class TodoGroupService {


    private final ClientRepository clientRepository;

    private final TodoGroupRepository todoGroupRepository;

    private final TodoService todoService;

    private final JwtProvider jwtProvider;

    @Autowired
    public TodoGroupService(JwtProvider jwtProvider, ClientRepository clientRepository, TodoGroupRepository todoGroupRepository, TodoService todoService){
        this.jwtProvider = jwtProvider;
        this.clientRepository = clientRepository;
        this.todoGroupRepository = todoGroupRepository;
        this.todoService = todoService;
    }
    public Long saveTodoGroup(String token, RequestTodoGroupDto requestTodoGroupDto){
        log.info("[SaveTodoGroup] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[SaveTodoGroup] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);
        log.info("[saveTodoGroup] 투두 그룹 생성중");
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 사용자입니다."));
        TodoGroup todoGroup = todoGroupRepository.save(requestTodoGroupDto.toEntity(client, requestTodoGroupDto));
        log.info("[saveTodoGroup] 투두 그룹 생성완료");
        return todoGroup.getGroupNum();
    }

    public void editTodoGroup(String token, Long todoGroupNum, RequestTodoGroupDto requestTodoGroupDto){
        log.info("[editTodoClient] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[editTodoClient] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("투두 그룹의 소유주가 맞는지 확인 작업 시작");

        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 사용자입니다."));

        if(client.getClientNum() != clientNum){
            throw new NotRightThisObject("투두 그룹 소유주가 아닙니다.");
        }

        log.info("[editTodoGroup] 투두 그룹 수정중");
        TodoGroup todoGroup = todoGroupRepository.findByGroupNum(todoGroupNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 투두 그룹입니다."));
        todoGroup.EditTodoGroup(requestTodoGroupDto);
        todoGroupRepository.save(todoGroup);
        log.info("[editTodoGroup] 투두 그룹 수정완료");
    }

    public List<ResponseTodoGroupDto> getAllTodoGroup(String token){
        log.info("[getAllTodoGroup] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[getAllTodoGroup] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("[getAllTodoGroup] 유저가 가지고 있는 전체 투두그룹을 데이터베이스에서 가져오는 작업");

        clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));

        List<TodoGroup> todoGroups = todoGroupRepository.findByClientClientNum(clientNum);

        List<ResponseTodoGroupDto> todoGroupDtos = todoGroups.stream()
                .map(todoGroup-> new ResponseTodoGroupDto(
                        todoGroup.getGroupNum(),
                        todoGroup.getGroupName(),
                        todoGroup.getIsImportant()
                ))
                .collect(Collectors.toList());
        log.info("[getAllTodoGroup] 전체 투두그룹 가져오기 성공");
        return todoGroupDtos;
    }

    @Transactional
    public void deleteTodoGroup(String token, Long TodoGroupNum){
        log.info("[deleteTodoGroup] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[deleteTodoGroup] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("[deleteTodoGroup] 투두 그룹 삭제 시작");
        log.info("[deleteTodoGroup] (1) 투두 그룹 실존여부 확인");
        TodoGroup todoGroup = todoGroupRepository.getByGroupNum(TodoGroupNum)
                        .orElseThrow(() -> new NotFoundElementException("존재하지 않는 투두 그룹입니다."));
        log.info("[deleteTodoGroup] (1) 확인완료");
        log.info("[deleteTodoGroup] (2) 투두 그룹을 지우려는 사용자가 그 그룹 소유주가 맞는지 확인");

        Client client = clientRepository.findByTodoGroupGroupNum(TodoGroupNum)
                        .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));
        if(client.getClientNum() != clientNum){
            throw new NotRightThisObject("투두 그룹 소유주가 아닙니다.");
        }
        log.info("[deleteTodoGroup] (2) 확인완료");
        log.info("[deleteTodoGroup] (3) 투두 그룹을 삭제하기 전 투두 그룹에 속한 투두들의 그룹을 null로 변환");

        todoGroup.getTodo().forEach(todo -> {
            todoService.editTodoGroup(todo);
        });
        log.info("[deleteTodoGroup] (3) 성공");
        log.info("[deleteTodoGroup] (4) 투두그룹에 연결된 투두리스트 비우기");
        todoGroup.makeTodoListEmpty();

        log.info(("[deleteTodoGroup] (4) 완료"));
        log.info("[deleteTodoGroup] (5) 투두 그룹 삭제");
        todoGroupRepository.delete(todoGroup);
        log.info("[deleteTodoGroup] 전체 작업 완료");
    }

    @Transactional(readOnly = true)
    public List<TodoListDto> getAllTodoForTodoGroup(String token){
        log.info("[getAllTodoForTodoGroup] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[getAllTodoForTodoGroup] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);

        log.info("[getAllTodoForTodoGroup] 유저가 가지고 있는 투두 그룹의 전체 투두 소환");

        clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));

        List<TodoGroup> todoGroups = todoGroupRepository.findByClientClientNum(clientNum);

        List<TodoListDto> todoListDtos = todoGroups.stream()
                .map( todoGroup -> new TodoListDto(
                        todoGroup.getGroupNum(),
                       todoGroup.getGroupName(),
                        todoGroup.getIsImportant(),
                        todoGroup.getTodo().stream().map(
                                todo->todo.toDto()
                                ).collect(Collectors.toList())
                )).collect(Collectors.toList());
        log.info("[getAllTodoForTodoGroup] 전체 투두 리스트 전달 성공");
        return todoListDtos;
    }

}
