package com.example.todo.user;

import com.example.todo.common.ValidationGroup;
import com.example.todo.user.dto.UserRequestDto;
import com.example.todo.user.dto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"User"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @ApiOperation(value = "User 정보 조회 api", notes = "특정 유저의 정보 조회")
    public ResponseEntity<UserResponseDto> getUser(
            @ApiParam(value = "User 식별자", required = true) @PathVariable Long userId) throws Exception {
        UserResponseDto userResponseDto = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @PostMapping()
    @ApiOperation(value = "새 User 등록 api", notes = "새로운 유저 정보 등록")
    public ResponseEntity<UserResponseDto> postUser(
            @Validated @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.saveUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @DeleteMapping("/{userId}")
    @ApiOperation(value = "기존 유저 정보 삭제 api", notes = "회원 탈퇴 등 유저 정보 삭제")
    public void deleteUser(@ApiParam(value = "User 식별자", required = true) @PathVariable Long userId) {
        userService.deleteUser(userId);
    }


    @PutMapping()
    @ApiOperation(value = "User 정보 수정 api", notes = "기존 유저 정보 수정")
    public ResponseEntity<UserResponseDto> putUser(
            @Validated(ValidationGroup.RUDValidationGroup.class) @RequestBody UserRequestDto userRequestDto) throws Exception {
        UserResponseDto userResponseDto = userService.updateUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }


}
