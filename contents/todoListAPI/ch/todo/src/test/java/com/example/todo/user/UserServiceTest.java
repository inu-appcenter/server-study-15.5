package com.example.todo.user;

import com.example.todo.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("testH2")
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void getUser() throws Exception {
        User mockUser =  new User("namu1234@gmail.com", "test1234", "namu");
        userRepository.save(mockUser);
        Long userId = mockUser.getUserId();

        given(userRepository.findById(userId)).willReturn(Optional.of(mockUser));

        UserResponseDto unvalidUserResponseDto = userService.getUser(userId);

        assertThat(unvalidUserResponseDto).isNotNull();
        assertThat(unvalidUserResponseDto.getUserId()).isEqualTo(userId);
        assertThat(unvalidUserResponseDto.getLoginId()).isEqualTo("namu1234@gmail.com");
        assertThat(unvalidUserResponseDto.getPassword()).isEqualTo("test1234");
        assertThat(unvalidUserResponseDto.getName()).isEqualTo("namu");
    }

}