package com.example.mytodolist.service;

import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.TodoResponseDto;
import com.example.mytodolist.dto.UserRequestDto;
import com.example.mytodolist.dto.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Import({UserService.class})
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("given_when_then 방식으로 getUser 테스트")
    void getUserTest(){

        //given
        User givenUser = User.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(givenUser));

        //when
        UserResponseDto userResponseDto = userService.getUser("USER");

        //then
        Assertions.assertEquals(userResponseDto.getId(),givenUser.getId());
        Assertions.assertEquals(userResponseDto.getName(),givenUser.getName());
        Assertions.assertEquals(userResponseDto.getEmail(),givenUser.getEmail());
        Assertions.assertEquals(userResponseDto.getLevel(),givenUser.getLevel());

        verify(userRepository).findById(1L);
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("given_when_then 방식으로 saveUser 테스트")
    void saveUserTest(){

        //given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        Mockito.when(userRepository.save(any(User.class)))
                .then(returnsFirstArg());

        //when
        UserResponseDto userResponseDto = userService.saveUser(userRequestDto);

        //then
        Assertions.assertEquals(userResponseDto.getName(),"홍길순");
        Assertions.assertEquals(userResponseDto.getEmail(),"popora99@naver.com");

        verify(userRepository).save(any());
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("given_when_then 방식으로 updateUser 테스트")
    void updateUserTest(){

        //given
        User givenUser = User.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("업데이트홍길순")
                .email("popora99@gmail.net")
                .build();


        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(givenUser));

        //when
        UserResponseDto userResponseDto = userService.updateUser("USER", userRequestDto);

        //then
        Assertions.assertEquals(userResponseDto.getName(),"업데이트홍길순");
        Assertions.assertEquals(userResponseDto.getEmail(),"popora99@gmail.net");

        verify(userRepository).findById(1L);
        verify(userRepository).save(any());
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("deleteUser 테스트")
    void deleteUserTest(){
        //when
        userService.deleteUser("USER");
        //then
        verify(userRepository).deleteById(1L);
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("getTodosByUserId 테스트")
    void getTodosByUserIdTest(){

        //given
        LocalDateTime deadLine = LocalDateTime.of(2023,12,31,00,00,00);

        User user = User.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();


        Todo todo = Todo.builder()
                .title("내일할일")
                .user(user)
                .contents("밥먹기")
                .deadLine(deadLine)
                .build();

        user.getTodoList().add(todo);

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        //when
        List<TodoResponseDto> result = userService.getTodosByUserId("USER");

        //then
        Assertions.assertEquals(1, result.size());
        TodoResponseDto todoResponseDto = result.get(0);
        Assertions.assertEquals(todo.getTitle(), todoResponseDto.getTitle());
        Assertions.assertEquals(todo.getContents(), todoResponseDto.getContent());


        verify(userRepository).findById(1L);
    }
}
