package com.example.TodoProject.controller;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.*;
import com.example.TodoProject.service.TodoGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.TodoProject.dto.TodoGroup.TodoGroupRequestDto.*;
import static com.example.TodoProject.dto.TodoGroup.TodoGroupResponseDto.*;
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

    @PostMapping("/{clientnum}")
    @Operation(summary = "투두 그룹 만들기", description = "clientNum과 RequestTodoGroupDto를 파라미터로 받음. 투두 그룹을 생성한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "투두 그룹 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")

    })
    public ResponseEntity<CommonResponseDto> createTodoGroup(@PathVariable Long clientnum, @Valid @RequestBody RequestTodoGroupDto requestTodoGroupDto){

        todoGroupService.saveTodoGroup(clientnum, requestTodoGroupDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 그룹 생성 성공", null));
    }

    @Operation(summary = "투두 그룹 수정", description = "clientNum과 RequestTodoGroupDto를 파라미터로 받음. 투두 그룹을 수정한다.")
    @PutMapping("/{todogroupnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 수정 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두 그룹입니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")
    })
    public ResponseEntity<CommonResponseDto> editTodoGroup(@PathVariable Long todogroupnum, @Valid @RequestBody RequestTodoGroupDto requestTodoGroupDto){

        todoGroupService.editTodoGroup(todogroupnum, requestTodoGroupDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 그룹 수정 성공", null));
    }

    @Operation(summary = "투두 그룹 조회", description = "투두 그룹 조회를 하는 컨트롤러")
    @GetMapping("/{clientnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 조회 성공", content = @Content(schema = @Schema(implementation = ResponseTodoGroupListDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 그룹 조회 성공", response = ResponseTodoGroupListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getAllTodoGroups(@PathVariable Long clientnum){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 그룹 전체 조회 성공", todoGroupService.getAllTodoGroup(clientnum)));
    }

    @Operation(summary = "투두 그룹을 가지고 있는 투두 전체 조회", description = "투두 그룹 조회를 하는 컨트롤러")
    @GetMapping("/todolist/{clientnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 조회 성공", content = @Content(schema = @Schema(implementation = ResponseTodoGroupListDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 그룹 조회 성공", response = ResponseTodoGroupListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getAllTodosIsTodoGroup(@PathVariable Long clientnum){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 그룹 전체 조회 성공", todoGroupService.getAllTodoForTodoGroup(clientnum)));
    }

}