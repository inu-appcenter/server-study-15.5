package com.example.todo.sign;

import com.example.todo.security.JwtProvider;
import com.example.todo.user.Authority;
import com.example.todo.user.User;
import com.example.todo.user.UserRepository;
import com.example.todo.user.dto.UserRequestDto;
import com.example.todo.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto login(UserRequestDto userRequestDto) throws Exception {

        User user = userRepository.findByLoginId(userRequestDto.getLoginId()).orElseThrow(() ->
                new BadCredentialsException("계정정보가 존재하지 않습니다."));

        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("계정정보가 존재하지 않습니다.");
        }

        return UserResponseDto.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .roles(user.getRoles())
                .token(jwtProvider.createToken(user.getLoginId(), user.getRoles()))
                .build();
    }


    /*
        예외처리 필요.
     */
    public UserResponseDto register(UserRequestDto userRequestDto) throws Exception {
        Authority at = new Authority("ROLE_USER");
        User user = User.builder()
                .loginId(userRequestDto.getLoginId())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .name(userRequestDto.getName())
                .build();
        user.assignRoles(Collections.singletonList(at));

        userRepository.save(user);

        return UserResponseDto.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .roles(user.getRoles())
                .build();
    }

    public void unregister(UserRequestDto userRequestDto) throws Exception {
        User user = userRepository.findByLoginId(userRequestDto.getLoginId()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));

        if (user.getPassword().equals(passwordEncoder.encode(userRequestDto.getPassword()))) {
            userRepository.delete(user);
        }

    }

}
