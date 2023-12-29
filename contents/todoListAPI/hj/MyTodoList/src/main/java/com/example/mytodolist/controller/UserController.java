package com.example.mytodolist.controller;


import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.*;
import com.example.mytodolist.service.SignService;
import com.example.mytodolist.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"유저 정보"},description = "회원가입, 로그인 ,조회,수정,삭제")
@Slf4j //private static final Logger log = LoggerFactory.getLogger(UserController.class) 자동 생성

@RequestMapping("/users") // 클라이언트의 요청과 요청을 처리하는 핸들러 메서드를 매핑
@RestController  //해당 클래스가 REST API의 리소스를 처리하기위한 API 엔드포인트로 동작함을 선언
@RequiredArgsConstructor //final 키워드가 붙은 필드에 생성자 주입
public class UserController {

    private final UserService userService;

    private final SignService signService;


    @ApiOperation(value="사용자 계정 조회", notes="userId에 해당하는 사용자 계정을 조회합니다.")
    @GetMapping()  //uri 경로를 제대로 고쳐야한다.
    public ResponseEntity<UserResponseDto> getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername(); // 로그인한 사용자의 아이디

        log.info("[UserGet] 로그인한 유저 id로 유저를 조회합니다. 유저 ID : {}",uid);
        UserResponseDto userResponseDto = userService.getUser(uid);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @ApiOperation(value = "사용자 계정 수정", notes = "userId에 해당하는 사용자 계정을 수정합니다.")
    @PutMapping("/update") //uri 경로를 제대로 고쳐야한다.
    public ResponseEntity<UserResponseDto> changeUser(@Valid @RequestBody UserRequestDto userRequestDto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername(); // 로그인한 사용자의 아이디

        log.info("[UserUpdate] 로그인한 유저 id 로 유저 정보를 수정합니다 유저 id : {}",uid);
        UserResponseDto userResponseDto = userService.updateUser(uid,userRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 계정 삭제", notes = "userId에 해당하는 사용자 계정을 삭제합니다.")
    @DeleteMapping("/delete") //uri 경로를 제대로 고쳐야한다.
    public ResponseEntity<Void> deleteUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername(); // 로그인한 사용자의 아이디

        log.info("[UserDelete]로그인한 유저 id로 유저를 삭제합니다. 유저 id : {}",uid);
        userService.deleteUser(uid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 TodoList 조회", notes = "userId에 해당하는 사용자 TodoList를 조회합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<TodoResponseDto>> getTodoList(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername(); // 로그인한 사용자의 아이디

        log.info("[UserGetTodo] 로그인한 유저 아이디로 해당 유저의 Todo 들을 조회합니다. 유저 id : {}",uid);
        List<TodoResponseDto> userTodoList = userService.getTodosByUserId(uid);

        return new ResponseEntity<>(userTodoList, HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 입력하여 로그인")
    @PostMapping("/sign-in")
    public SignInResultDto signIn(@ApiParam(value = "ID", required = true)@RequestParam String id,
                                  @ApiParam(value = "Password",required = true)@RequestParam String password){
        log.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****",id);
        SignInResultDto signInResultDto = signService.signIn(id,password);

        if(signInResultDto.getToken() !=null){
            log.info("[signIn] 정상적으로 로그인 되었습니다 : id : {}, token: {}",id,signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @ApiOperation(value = "회원가입", notes = "아이디,비밀번호,이름,이메일,권한을 입력하여 회원가입")
    @PostMapping( "/sign-up")
    public SignUpResultDto signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        String role = "USER";
        log.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, email : {}, role : {}",signUpRequestDto.getUid(),signUpRequestDto.getName(),signUpRequestDto.getEmail(),role);
        signService.checkDuplicate(signUpRequestDto.getUid());

        SignUpResultDto signUpResultDto = signService.signUp(signUpRequestDto,role);

        log.info("[signUp] 회원가입을 완료했습니다. id : {}",signUpRequestDto.getUid());

        return signUpResultDto;
    }

}
