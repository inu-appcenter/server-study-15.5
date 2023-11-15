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
        User selectedUser = userRepository.findById(id)
                .orElseThrow(() -> new Exception("해당하는 User 엔티티를 찾을 수 없습니다."));

        return selectedUser.toResponseDto();
    }

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = userRequestDto.toEntity();
        User savedUser = userRepository.save(user);
        return savedUser.toResponseDto();
    }


    private UserResponseDto updateAndSaveUserFromDto(User user, UserRequestDto userRequestDto) {
        user.updateFromDto(userRequestDto);
        return userRepository.save(user)
                .toResponseDto();
    }

    public UserResponseDto updateUser(UserRequestDto userRequestDto) throws Exception {
        Optional<User> selectedUser = userRepository.findById(userRequestDto.getUserId());
        if (selectedUser.isPresent()) {
            return updateAndSaveUserFromDto(selectedUser.get(), userRequestDto);
        }
        return saveUser(userRequestDto);
    }


    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
