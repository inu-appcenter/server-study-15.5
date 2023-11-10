package com.example.todo.user;

import com.example.todo.user.dto.UserRequestDto;
import com.example.todo.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUser(Long id) throws Exception {
        Optional<User> selectedUser = userRepository.findById(id);
        selectedUser.orElseThrow(() -> new Exception("해당하는 User 엔티티를 찾을 수 없습니다."));

        return User.toResponseDto(selectedUser.get());
    }

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = UserRequestDto.toEntity(userRequestDto);
        User savedUser = userRepository.save(user);
        return User.toResponseDto(savedUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
