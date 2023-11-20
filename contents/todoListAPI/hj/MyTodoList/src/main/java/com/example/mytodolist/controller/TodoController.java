package com.example.mytodolist.controller;


import com.example.mytodolist.dto.TodoRequestDto;
import com.example.mytodolist.dto.TodoResponseDto;
import com.example.mytodolist.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Api(tags = {"Todo 정보"},description = "Todo 생성,조회,수정,삭제")
@Slf4j

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;


    @ApiOperation(value="id 번호로 Todo 조회", notes="TodoId에 해당하는  Todo를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodo(
            @ApiParam(value="투두 번호",required = true, example = "1")
            @PathVariable("id") Long id) throws NoSuchElementException {
        log.info("[TodoGet] 투두 번호로 투두를 조회합니다. 투두 id : {}",id);
        TodoResponseDto todoResponseDto = todoService.getTodo(id);

        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 id 에 해당하는 Todo 생성", notes = "userId 에 해당하는 사용자로 게시물을 작성합니다.")
    @PostMapping("/{id}")
    public ResponseEntity<TodoResponseDto> createTodo(
            @ApiParam(value="사용자 아이디",required = true, example = "1")
            @PathVariable("id") Long id, @Valid @RequestBody TodoRequestDto todoRequestDto)throws NoSuchElementException{

        log.info("[TodoPost] 유저 아이디에 해당하는 투두를 생성합니다. 유저 id : {}",id);
        TodoResponseDto todoResponseDto = todoService.saveTodo(id,todoRequestDto); //유저를 찾고, 예외처리를 서비스로 넘김

        return new ResponseEntity<>(todoResponseDto,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Todo 수정", notes = "todoId에  해당하는 Todo 를 수정합니다.")
    @PutMapping("/{id}") //uri 경로를 제대로 고쳐야한다.
    public ResponseEntity<TodoResponseDto> updateTodo(
            @ApiParam(value="투두 번호",required = true, example = "1")
            @PathVariable("id") Long id,@Valid @RequestBody TodoRequestDto todoRequestDto )throws NoSuchElementException{

        log.info("[TodoUpdate] 투두 번호에 해당하는 투두를 수정합니다. 투두 id : {}",id);
        TodoResponseDto todoResponseDto = todoService.updateTodo(id,todoRequestDto);

        return new ResponseEntity<>(todoResponseDto,HttpStatus.OK);
    }

    @ApiOperation(value = "Todo 삭제", notes = "todoId에 해당하는 Todo 를 삭제합니다.")
    @DeleteMapping("/{id}") //uri 경로를 제대로 고쳐야한다.
    public ResponseEntity<Void> deleteTodo(
            @ApiParam(value="투두 번호",required = true, example = "1")
            @PathVariable("id") Long id) throws NoSuchElementException{

        log.info("[TodoDelete] 투두 번호에 해당하는 투두를 삭제합니다. 투두 id : {}",id);
        todoService.deleteTodo(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Todo 성공여부 수정", notes = "todoId에 해당하는 Todo 의 성공여부를 수정합니다.")
    @PutMapping("/check")
    public ResponseEntity<TodoResponseDto> checkCompletedTodo(
            @ApiParam(value="투두 번호",required = true, example = "1")
            @RequestParam("id") Long id,
            @ApiParam(value="투두 성공여부 체크")
            @RequestParam("completed") Boolean completed) throws NoSuchElementException{

        log.info("[TodoCheck] 투두 번호에 해당하는 투두의 성공여부를 체크합니다. 투두 id : {}, 성공여부 : {}",id,completed);
        TodoResponseDto todoResponseDto = todoService.checkCompleted(id,completed);

        return new ResponseEntity<>(todoResponseDto,HttpStatus.OK);
    }

}
