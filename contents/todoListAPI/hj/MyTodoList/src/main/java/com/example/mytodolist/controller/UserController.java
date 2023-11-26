package com.example.mytodolist.controller;

import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.TodoResponseDto;
import com.example.mytodolist.dto.UserRequestDto;
import com.example.mytodolist.dto.UserResponseDto;
import com.example.mytodolist.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Api(tags = {"유저 정보"},description = "유저 생성,조회,수정,삭제")
@Slf4j //private static final Logger log = LoggerFactory.getLogger(UserController.class) 자동 생성

@RequestMapping("/users") // 클라이언트의 요청과 요청을 처리하는 핸들러 메서드를 매핑
@RestController  //해당 클래스가 REST API의 리소스를 처리하기위한 API 엔드포인트로 동작함을 선언
@RequiredArgsConstructor //final 키워드가 붙은 필드에 생성자 주입
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @ApiOperation(value="사용자 계정 조회", notes="userId에 해당하는 사용자 계정을 조회합니다.")
    @GetMapping("/{id}")  //uri 경로를 제대로 고쳐야한다.
    public ResponseEntity<UserResponseDto> getUser(
            @ApiParam(value="유저 아이디",required = true, example = "1") @PathVariable("id") Long id) throws NoSuchElementException{

        log.info("[UserGet] 유저 id로 유저를 조회합니다. 유저 ID : {}",id);
        User user = userRepository.getById(id);
        UserResponseDto userResponseDto = userService.getUser(user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @ApiOperation(value = "사용자 계정 생성", notes = "사용자 계정을 생성합니다.")
    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto userRequestDto)throws NoSuchElementException{
        log.info("[UserPost] 유저를 생성합니다.");
        UserResponseDto userResponseDto = userService.saveUser(userRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "사용자 계정 수정", notes = "userId에 해당하는 사용자 계정을 수정합니다.")
    @PutMapping("/{id}") //uri 경로를 제대로 고쳐야한다.
    public ResponseEntity<UserResponseDto> changeUser(
            @ApiParam(value = "유저 아이디",required = true,example = "1")
            @PathVariable("id") Long id,@Valid @RequestBody UserRequestDto userRequestDto)throws NoSuchElementException{

        log.info("[UserUpdate]유저 id로 유저 정보를 수정합니다 유저 id : {}",id);
        UserResponseDto userResponseDto = userService.updateUser(id,userRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 계정 삭제", notes = "userId에 해당하는 사용자 계정을 삭제합니다.")
    @DeleteMapping("/{id}") //uri 경로를 제대로 고쳐야한다.
    public ResponseEntity<Void> deleteUser(
            @ApiParam(value = "유저 아이디",required = true,example = "1")
            @PathVariable("id") Long id) throws NoSuchElementException{

        log.info("[UserDelete]유저 id로 유저를 삭제합니다. 유저 id : {}",id);
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 TodoList 조회", notes = "userId에 해당하는 사용자 TodoList를 조회합니다.")
    @GetMapping("/search/{id}")
    public ResponseEntity<List<TodoResponseDto>> getTodoList(
            @ApiParam(value = "유저 아이디",required = true,example = "1")
            @PathVariable("id") Long id)throws NoSuchElementException{

        log.info("[UserGetTodo] 유저 아이디로 해당 유저의 Todo 들을 조회합니다. 유저 id : {}",id);
        List<TodoResponseDto> userTodoList = userService.getTodosByUserId(id);

        return new ResponseEntity<>(userTodoList, HttpStatus.OK);
    }
}
