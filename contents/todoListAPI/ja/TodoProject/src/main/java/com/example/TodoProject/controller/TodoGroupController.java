package com.example.TodoProject.controller;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.CommonResponseDto;
import com.example.TodoProject.dto.RequestTodoDto;
import com.example.TodoProject.dto.RequestTodoGroupDto;
import com.example.TodoProject.service.TodoGroupService;
import com.example.TodoProject.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(responseCode = "200", description = "투두 그룹 생성 성공")
    })
    public ResponseEntity<CommonResponseDto> createTodoGroup(Long clientNum, @RequestBody RequestTodoGroupDto requestTodoGroupDto){
        todoGroupService.saveTodoGroup(clientNum, requestTodoGroupDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 그룹 생성 성공", "null"));
    }

    @Operation(summary = "투두 그룹 수정", description = "clientNum과 RequestTodoGroupDto를 파라미터로 받음. 투두 그룹을 수정한다.")
    @PostMapping("/edit")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 수정 성공")
    })
    public ResponseEntity<CommonResponseDto> editTodoGroup(Long clientNum, @RequestBody RequestTodoGroupDto requestTodoGroupDto){

        todoGroupService.editTodoGroup(clientNum, requestTodoGroupDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 그룹 수정 성공", "null"));
    }

}
