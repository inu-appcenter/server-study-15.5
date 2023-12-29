package com.example.TodoProject.controller;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.*;
import com.example.TodoProject.service.TodoGroupService;
import io.swagger.annotations.Api;
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
@RequestMapping("/todogroup")
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

    @PostMapping("")
    @Operation(summary = "투두 그룹 만들기", description = "투두 그룹을 생성한다. <br><br> 입력: <br>1. 사용자의 데이터베이스 상 pk(clientnum)<br> 2. RequestTodoGroupDto<br> 출력: data에 만들어진 투두그룹의 num을 반환한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "투두 그룹 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")

    })
    public ResponseEntity<CommonResponseDto> createTodoGroup(@RequestHeader("X-AUTH-TOKEN") String token, @Valid @RequestBody RequestTodoGroupDto requestTodoGroupDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 그룹 생성 성공", todoGroupService.saveTodoGroup(token, requestTodoGroupDto)));
    }

    @Operation(summary = "투두 그룹 수정", description = "투두 그룹을 수정한다.<br><br> 입력: <br> 1. 수정하려는 투두그룹의 데이터베이스 상 pk(todogroupnum)<br> 2. RequestTodoGroupDto<br><br> 출력: data에 null을 반환함.")
    @PutMapping("/{todogroupnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 수정 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두 그룹입니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")
    })
    public ResponseEntity<CommonResponseDto> editTodoGroup(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long todogroupnum, @Valid @RequestBody RequestTodoGroupDto requestTodoGroupDto){

        todoGroupService.editTodoGroup(token ,todogroupnum, requestTodoGroupDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 그룹 수정 성공", null));
    }

    @Operation(summary = "투두 그룹 조회", description = "투두 그룹 조회를 하는 컨트롤러<br><br> 입력: 투두 그룹을 조회하려는 사용자의 데이터베이스 상 pk(clientnum)<br>출력: data에 ResponseTodoGroup 형식을 List에 담아서 반환한다.")
    @GetMapping("/todogroups")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 조회 성공", content = @Content(schema = @Schema(implementation = ResponseTodoGroupListDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 그룹 조회 성공", response = ResponseTodoGroupListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getAllTodoGroups(@RequestHeader("X-AUTH-TOKEN") String token){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 그룹 전체 조회 성공", todoGroupService.getAllTodoGroup(token)));
    }

    @Operation(summary = "투두 그룹을 가지고 있는 투두 전체 조회", description = "투두 그룹을 가지고 있는 투두를 전체 조회한다. 투두 그룹이 없는 투두는 TodoController에서 조회한다. <br><br> 입력: 이 조회 기능을 사용하려는 사용자의 데이터베이스 상 pk(clientnum)<br> 출력: data에 ResponseTodoGroup 형식을 List에 담아서 출력함.")
    @GetMapping("/todolist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 조회 성공", content = @Content(schema = @Schema(implementation = ResponseTodoGroupListDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 그룹 조회 성공", response = ResponseTodoGroupListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getAllTodosIsTodoGroup(@RequestHeader("X-AUTH-TOKEN") String token){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 그룹 전체 조회 성공", todoGroupService.getAllTodoForTodoGroup(token)));
    }

    @Operation(summary = "투두 그룹 삭제", description = "투두 그룹을 삭제합니다. <br><br> 입력: <br> 1. 삭제하려는 투두 그룹 소유자의 데이터베이스 상 pk(clientnum).<br> 2. 삭제하려는 투두그룹의 데이터베이스 상 pk(todogorupnum)<br><br> 출력: data에 null 전달 ")
    @DeleteMapping("/{todogroupnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두 그룹입니다."),
            @ApiResponse(responseCode = "403", description = "투두 그룹의 소유주가 아닙니다.")
    })
    public ResponseEntity<CommonResponseDto> deleteTodoGroup(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long todogroupnum){
        todoGroupService.deleteTodoGroup(token,todogroupnum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 그룹 삭제 성공",null));
    }
}
