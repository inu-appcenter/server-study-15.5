package com.example.todo.user;

import com.example.todo.user.dto.UserRequestDto;
import com.example.todo.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUser(Long id) {
        User user = userRepository.getById(id);

        return UserResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .build();
    }

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .name(userRequestDto.getName())
                .loginId(userRequestDto.getLoginId())
                .password(userRequestDto.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .loginId(savedUser.getLoginId())
                .password(savedUser.getPassword())
                .build();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
