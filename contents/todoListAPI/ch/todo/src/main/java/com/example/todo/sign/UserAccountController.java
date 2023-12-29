package com.example.todo.sign;

import com.example.todo.user.dto.UserRequestDto;
import com.example.todo.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping()
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) throws Exception {
        return ResponseEntity.ok().body(userAccountService.register(userRequestDto));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserResponseDto> signin(@RequestBody UserRequestDto userRequestDto) throws Exception {
        return ResponseEntity.ok().body(userAccountService.login(userRequestDto));
    }

    @DeleteMapping()
    public void unregister(@RequestBody UserRequestDto userRequestDto) throws Exception {
        userAccountService.unregister(userRequestDto);
    }
}
