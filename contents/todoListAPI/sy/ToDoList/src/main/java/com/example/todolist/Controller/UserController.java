package com.example.todolist.Controller;

import com.example.todolist.DTO.User.AddUserReqDTO;
import com.example.todolist.DTO.User.ChangeUserReqDTO;
import com.example.todolist.DTO.User.DeleteUserReqDTO;
import com.example.todolist.DTO.User.ReadUserResDTO;
import com.example.todolist.Service.UserService;
import javax.servlet.ServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "User")
public class UserController {

    private final UserService userService;
    private final Long userId = 4l;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/users")
    @ApiOperation(value = "유저 회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 생성성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<Void> addUser(@RequestBody @Valid AddUserReqDTO addUserReqDTO){
        userService.addUser(addUserReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/users")
    @ApiOperation(value = "유저 정보조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 조회성공",response = ReadUserResDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<ReadUserResDTO> readUser(ServletRequest request){
        /*
            토큰에서 userId값 추출 로직
        */
        ReadUserResDTO readUserResDTO = userService.readUserInfo(userId);

        return ResponseEntity.status(HttpStatus.OK).body(readUserResDTO);
    }

    @PatchMapping("/users")
    @ApiOperation(value = "유저 정보변경")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 정보변경 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<Void> changeUserInfo(@RequestBody @Valid ChangeUserReqDTO changeUserReqDTO, ServletRequest request){
        /*
            토큰에서 userId값 추출 로직
        */
        changeUserReqDTO.setUserId(userId);
        userService.changeUserInfo(changeUserReqDTO);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @DeleteMapping("/users")
    @ApiOperation(value = "유저 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 삭제성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<Void> deleteUser(@RequestBody @Valid DeleteUserReqDTO deleteUserReqDTO, ServletRequest request){
        /*
            토큰에서 userId값 추출 로직
        */
        deleteUserReqDTO.setUserId(userId);
        userService.deleteUser(deleteUserReqDTO);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
