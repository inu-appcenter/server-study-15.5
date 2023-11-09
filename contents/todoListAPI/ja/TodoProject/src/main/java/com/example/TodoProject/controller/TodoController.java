package com.example.TodoProject.controller;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.CommonResponseDto;
import com.example.TodoProject.dto.RequestTodoDto;
import com.example.TodoProject.dto.ResponseTodoDto;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class TodoController {

    /*
    기능을 추가하자
        1. 투두 작성하기
        2. 투두 수정하기
        3. 투두 삭제하기
        4. 투두 검색하기
     */
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    //투두 작성하기
    @Operation(summary = "투두 만들기", description = "clientNum과 RequestTodoDto를 파라미터로 받음. 유저에게 투두를 생성한다.")
    @PostMapping("/create")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "투두 생성 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 투두 그룹입니다.")
    })
    public ResponseEntity<CommonResponseDto> createTodo(Long clientNum,@RequestBody RequestTodoDto requestTodoDto){
        if(requestTodoDto.getTodoGroupNum() == null){
            todoService.saveForNotTodoGroup(clientNum, requestTodoDto);
        } else if (requestTodoDto.getTodoGroupNum() != null) {
            todoService.saveForTodoGroup(clientNum, requestTodoDto);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두리스트 생성 성공", "null"));
    }

    //투두 수정하기
    @PutMapping("/patch/{todonum}")
    @Operation(summary = "투두 수정하기", description = "todoNum과 RequestTodoDto를 파라미터로 받음. 투두 수정을 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 투두입니다.")
    })
    public ResponseEntity<CommonResponseDto> editTodo(@PathVariable Long todonum,@RequestBody RequestTodoDto requestTodoDto){
        if(requestTodoDto.getTodoGroupNum() == null){
            todoService.editTodoForNotTodoGroup(todonum, requestTodoDto);
        }
        else{
            todoService.editTodoForTodoGroup(todonum, requestTodoDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 수정 성공", "null"));
    }

    //투두 삭제하기
    @Operation(summary = "투두 삭제하기", description = "todoNum을 파라미터로 받음. 투두를 영구 삭제한다.")
    @DeleteMapping("/delete/{todonum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 삭제 성공"),
            @ApiResponse(responseCode= "400", description = "존재하지 않는 투두입니다.")
    })
    public ResponseEntity<CommonResponseDto> deleteTodo(@PathVariable Long todonum){
        todoService.deleteTodo(todonum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 삭제 성공" ,todonum));
    }

    @GetMapping ("/title/{clientnum}/{keyword}")
    @Operation(summary = "투두 제목 검색하기", description = "투두를 검색하는 기능이다. keyword가 포함되어있는 제목의 투두를 모두 출력한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저입니다.")
    })
    public ResponseEntity<CommonResponseDto> getTodoSearch(@PathVariable Long clientnum, @PathVariable String keyword) {
        List<ResponseTodoDto> todos = todoService.getUsersAllTodos(clientnum);
        List<ResponseTodoDto> matchingTodos = todos.stream()
                .filter(todo -> todo.getTodoTitle().contains(keyword))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"검색 성공",matchingTodos));
    }

    @PutMapping("toggle/{todonum}")
    public ResponseEntity<CommonResponseDto> toggleTodo(@PathVariable Long todonum){
        todoService.toggleTodo(todonum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"토글 성공", "null"));
    }

}
