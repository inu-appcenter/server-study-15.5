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
    @Operation(summary = "투두 만들기", description = "유저가 작성한 투두를 생성한다.<br><br> 특이사항: 투두 작성 시 유저가 투두가 속할 그룹을 선택하지 않았다면(혹은 없음 으로 설정했다면.) todogroupnum 자리에 null을 넣어주셔야 합니다. 만약 선택했다면 저 자리에 투두그룹의 데이터베이스 상 pk를 넣으시면 됩니다. <br><br> 입력: ResponseTodoDto <br> 출력: data에 null로 반환")
    @PostMapping("")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "투두 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두 그룹입니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")
    })
    public ResponseEntity<CommonResponseDto> createTodo(@RequestHeader("X-AUTH-TOKEN") String token, @Valid @RequestBody RequestTodoDto requestTodoDto){

        todoService.createTodo(token, requestTodoDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두리스트 생성 성공", null));
    }

    //투두 수정하기
    @Operation(summary = "투두 수정하기", description = "투두 수정을 한다. <br><br> 입력: RequestTodoDto <br> 출력: data에 null을 반환")
    @PutMapping("/{todonum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 수정 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두입니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")
    })
    public ResponseEntity<CommonResponseDto> editTodo(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long todonum,@Valid @RequestBody RequestTodoDto requestTodoDto){

        todoService.editTodo(token, todonum, requestTodoDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "투두 수정 성공", null));
    }

    //투두 삭제하기
    @Operation(summary = "투두 삭제하기", description = "투두를 영구 삭제한다.<br><br> 입력: todo의 데이터베이스 상 pk(todonum)<br> 출력: 삭제한 투두의 데이터베이스 상 pk(todonum)")
    @DeleteMapping("/{todonum}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 삭제 성공",content = @Content(schema = @Schema(implementation = ResponseTodoDeleteDto.class))),
            @ApiResponse(responseCode= "404", description = "존재하지 않는 투두입니다."),
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 삭제 성공", response = ResponseTodoDeleteDto.class)
    )
    public ResponseEntity<CommonResponseDto> deleteTodo(@RequestHeader("X-AUTH-TOKEN") String token,@PathVariable Long todonum){
        todoService.deleteTodo(token, todonum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"투두 삭제 성공" ,todonum));
    }

    @GetMapping ("/{keyword}")
    @Operation(summary = "투두 제목 검색하기", description = "투두를 검색하는 기능이다. keyword가 포함되어있는 제목의 투두를 모두 출력한다.<br><br> 입력: <br>  1. 검색하는 유저의 데이터베이스 상 pk(clientnum) <br> 2. 검색할 단어(keyword) <br><br> 출력: data에 제목에 검색어가 포함된 투두의 ResponseTodoDto를 list로 반환함.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 검색 성공", content = @Content(schema = @Schema(implementation = ResponseListDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 검색 성공", response = ResponseListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getTodoSearch(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable String keyword) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"검색 성공", todoService.getUsersSearchTodos(token,keyword)));
    }

    @Operation(summary = "투두 그룹을 가지고 있지 않은 투두 전체 조회", description = "투두 그룹을 가지고 있지 않은 투두를 조회를 하는 컨트롤러.<br><br> 입력: 사용자의 데이터베이스 상 pk(clientnum)<br>출력: data에 투두 그룹이 null인 투두들을 ResponseTodoDto 형식으로 바꾼 후, list로 반환")
    @GetMapping("/todolist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 그룹을 가지고 있지 않은 투두 전체 조회", content = @Content(schema = @Schema(implementation = ResponseListDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "투두 검색 성공", response = ResponseListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getAllTodosNotTodoGroup(@RequestHeader("X-AUTH-TOKEN") String token){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "getAllTodosNotTodoGroup 전체 조회 성공", todoService.getAllTodosForNotTodoGroup(token)));
    }
}
