package com.example.todo.user;

import com.example.todo.user.dto.UserRequestDto;
import com.example.todo.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    /*
    public UserResponseDto updateUser(UserRequestDto userRequestDto) throws Exception {
        Optional<User> selectedUser = userRepository.findById(userRequestDto.getUserId());
        User puttedUser;
        User savedUser;
        try {
            selectedUser.orElseThrow(() -> new Exception("해당하는 User 엔티티를 찾을 수 없습니다."));
            puttedUser = selectedUser.get();
            puttedUser.updateFromDto(userRequestDto);
            savedUser = userRepository.save(puttedUser);
        } catch (Exception taskNotFoundException) {
            Optional<User> taskOwner = userRepository.findById(userRequestDto.getUserId());
            taskOwner.orElseThrow(() -> new Exception("해당 Task와 User의 정보를 찾을 수 없음"));
            puttedUser = userRequestDto.toEntity(userRequestDto);
            savedUser = userRepository.save(puttedUser);
        }
        return User.toResponseDto(savedUser);
    }
     */

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
