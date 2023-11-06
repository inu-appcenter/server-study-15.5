package com.example.todolist.Controller;

import com.example.todolist.DTO.User.AddUserReqDTO;
import com.example.todolist.DTO.User.ChangeUserReqDTO;
import com.example.todolist.DTO.User.DeleteUserReqDTO;
import com.example.todolist.DTO.User.ReadUserResDTO;
import com.example.todolist.Service.UserService;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody AddUserReqDTO addUserReqDTO){

        userService.addUser(addUserReqDTO);

        return ResponseEntity.status(201).body(null);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> readUser(ServletRequest request){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 1l; // 임시로 userId값 설정
        ReadUserResDTO readUserResDTO = userService.readUserInfo(userId);

        return ResponseEntity.status(200).body(readUserResDTO);
    }

    @PatchMapping("/users")
    public ResponseEntity<Object> changeUserInfo(@RequestBody ChangeUserReqDTO changeUserReqDTO, ServletRequest request){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 1l; // 임시로 userId값 설정
        userService.changeUserInfo(changeUserReqDTO);

        return ResponseEntity.status(200).body(null);
    }
    @DeleteMapping("/users")
    public ResponseEntity<Object> deleteUser(@RequestBody DeleteUserReqDTO deleteUserReqDTO){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 1l; // 임시로 userId값 설정
        userService.deleteUser(deleteUserReqDTO);

        return ResponseEntity.status(200).body(null);
    }
}
