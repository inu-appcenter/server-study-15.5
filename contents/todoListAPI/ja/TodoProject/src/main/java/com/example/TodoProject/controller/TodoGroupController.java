package com.example.TodoProject.controller;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.*;
import com.example.TodoProject.service.TodoGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todoGroup")
public class TodoGroupController {

    /*
    기능을 추가해보자
        1. 그룹 만들기
        2. 그룹 수정하기
     */
    private final TodoGroupService todoGroupService;

    @Autowired
    public TodoGroupController(TodoGroupService todoGroupService){
        this.todoGroupService = todoGroupService;
    }

    @PostMapping("/create")
    @Operation(summary = "투두 그룹 만들기", description = "clientNum과 RequestTodoGroupDto를 파라미터로 받음. 투두 그룹을 생성한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "투두 그룹 생성 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저입니다."),

    })
    public ResponseEntity<CommonResponseDto> createTodoGroup(Long clientNum, @RequestBody RequestTodoGroupDto requestTodoGroupDto){
        todoGroupService.saveTodoGroup(clientNum, requestTodoGroupDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 그룹 생성 성공", "null"));
    }

    @Operation(summary = "투두 그룹 수정", description = "clientNum과 RequestTodoGroupDto를 파라미터로 받음. 투두 그룹을 수정한다.")
    @PostMapping("/patch/{todogroupnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 투두 그룹입니다.")
    })
    public ResponseEntity<CommonResponseDto> editTodoGroup(Long todogroupnum, @RequestBody RequestTodoGroupDto requestTodoGroupDto){

        todoGroupService.editTodoGroup(todogroupnum, requestTodoGroupDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 그룹 수정 성공", "null"));
    }

    @Operation(summary = "투두 그룹 조회", description = "투두 그룹 조회를 하는 컨트롤러")
    @PostMapping("")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 조회 성공")
    })
    public ResponseEntity<CommonResponseDto> getAllTodoGroups(Long clientNum){
        List<RequestTodoGroupDto> todoGroups = todoGroupService.getAllTodoGroup(clientNum);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 그룹 전체 조회 성공", todoGroups));
    }

    @Operation(summary = "투두 그룹, 투두 전체 조회", description = "투두 그룹 조회를 하는 컨트롤러")
    @PostMapping("/all/{clientnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 조회 성공")
    })
    public ResponseEntity<CommonResponseDto> getAllTodoGroupsAndTodos(@PathVariable Long clientnum){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 그룹 전체 조회 성공", todoGroupService.getAllTodoGroupsTodo(clientnum)));
    }
}
