package com.example.mytodolist.controller;

import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.TodoRequestDto;
import com.example.mytodolist.dto.TodoResponseDto;
import com.example.mytodolist.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Todo 정보"},description = "Todo 생성,조회,수정,삭제")

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UserRepository userRepository;


    @ApiOperation(value="id 번호로 Todo 조회", notes="TodoId에 해당하는  Todo를 조회합니다.")
    @GetMapping("{id}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable("id") Long id) throws RuntimeException{
        TodoResponseDto todoResponseDto = todoService.getTodo(id);

        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 id 에 해당하는 Todo 생성", notes = "userId 에 해당하는 사용자로 게시물을 작성합니다.")
    @PostMapping("{id}")
    public ResponseEntity<TodoResponseDto> createTodo(@PathVariable("id") Long id, @RequestBody TodoRequestDto todoRequestDto){
        User user = userRepository.findById(id).orElseThrow(()-> new NullPointerException());

        TodoResponseDto todoResponseDto = todoService.saveTodo(user.getId(),todoRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Todo 수정", notes = "todoId에  해당하는 Todo 를 수정합니다.")
    @PutMapping("{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable("id") Long id,@RequestBody TodoRequestDto todoRequestDto ) throws RuntimeException{
        TodoResponseDto todoResponseDto = todoService.updateTodo(id,todoRequestDto);

        return new ResponseEntity<>(todoResponseDto,HttpStatus.OK);
    }

    @ApiOperation(value = "Todo 삭제", notes = "todoId에 해당하는 Todo 를 삭제합니다.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) throws RuntimeException{
        todoService.deleteTodo(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Todo 성공여부 수정", notes = "todoId에 해당하는 Todo 의 성공여부를 수정합니다.")
    @PutMapping("/check")
    public ResponseEntity<TodoResponseDto> checkCompletedTodo(@RequestParam("id") Long id, @RequestParam("isCompleted") String isCompleted) throws RuntimeException{
        TodoResponseDto todoResponseDto = todoService.checkCompleted(id,isCompleted);

        return new ResponseEntity<>(todoResponseDto,HttpStatus.OK);
    }


}
