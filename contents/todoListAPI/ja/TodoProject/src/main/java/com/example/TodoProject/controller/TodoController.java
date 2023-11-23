package com.example.TodoProject.controller;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.Client.ClientResponseDto;
import com.example.TodoProject.dto.CommonResponseDto;
import com.example.TodoProject.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.TodoProject.dto.Todo.TodoRequestDto.*;
import static com.example.TodoProject.dto.Todo.TodoResponseDto.*;

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
    @PostMapping("/{clientnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "투두 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두 그룹입니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")
    })

    public ResponseEntity<CommonResponseDto> createTodo(@PathVariable Long clientnum, @Valid @RequestBody RequestTodoDto requestTodoDto){

        if(requestTodoDto.getTodoGroupNum() == null){
            todoService.saveForNotTodoGroup(clientnum, requestTodoDto);
        } else if (requestTodoDto.getTodoGroupNum() != null) {
            todoService.saveForTodoGroup(clientnum, requestTodoDto);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두리스트 생성 성공", null));
    }

    //투두 수정하기
    @Operation(summary = "투두 수정하기", description = "todoNum과 RequestTodoDto를 파라미터로 받음. 투두 수정을 한다.")
    @PutMapping("/{todonum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 수정 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두입니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")
    })
    public ResponseEntity<CommonResponseDto> editTodo(@PathVariable Long todonum,@Valid @RequestBody RequestTodoDto requestTodoDto){

        if(requestTodoDto.getTodoGroupNum() == null){
            todoService.editTodoForNotTodoGroup(todonum, requestTodoDto);
        }
        else{
            todoService.editTodoForTodoGroup(todonum, requestTodoDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 수정 성공", null));
    }

    //투두 삭제하기
    @Operation(summary = "투두 삭제하기", description = "todoNum을 파라미터로 받음. 투두를 영구 삭제한다.")
    @DeleteMapping("/{todonum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 삭제 성공",content = @Content(schema = @Schema(implementation = ResponseTodoDeleteDto.class))),
            @ApiResponse(responseCode= "404", description = "존재하지 않는 투두입니다."),
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 삭제 성공", response = ResponseTodoDeleteDto.class)
    )
    public ResponseEntity<CommonResponseDto> deleteTodo(@PathVariable Long todonum){
        todoService.deleteTodo(todonum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 삭제 성공" ,todonum));
    }

    @GetMapping ("/{clientnum}/{keyword}")
    @Operation(summary = "투두 제목 검색하기", description = "투두를 검색하는 기능이다. keyword가 포함되어있는 제목의 투두를 모두 출력한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 검색 성공", content = @Content(schema = @Schema(implementation = ResponseListDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 검색 성공", response = ResponseListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getTodoSearch(@PathVariable Long clientnum, @PathVariable String keyword) {
        List<ResponseTodoDto> todos = todoService.getUsersAllTodos(clientnum);
        List<ResponseTodoDto> matchingTodos = todos.stream()
                .filter(todo -> todo.getTodoTitle().contains(keyword))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"검색 성공",matchingTodos));
    }

    @PutMapping("/toggle/{todonum}")
    @Operation(summary = "투두 토글", description = "todonum을 인자로 받으면 그 todo의 isFinished(Boolean)을 반전시켜준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두입니다.")
    })
    public ResponseEntity<CommonResponseDto> toggleTodo(@PathVariable Long todonum){
        todoService.toggleTodo(todonum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"토글 성공", null));
    }


    @Operation(summary = "투두 그룹을 가지고 있지 않은 투두 전체 조회", description = "투두 그룹을 가지고 있지 않은 투두를 조회를 하는 컨트롤러. clientnum을 인자로 넣으면 responseTodoDto를 List 형식으로 반환함.")
    @GetMapping("/{clientnum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹 조회 성공", content = @Content(schema = @Schema(implementation = ResponseListDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 검색 성공", response = ResponseListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getAllTodosNotTodoGroup(@PathVariable Long clientnum){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 그룹 전체 조회 성공", todoService.getAllTodosForNotTodoGroup(clientnum)));
    }
}
