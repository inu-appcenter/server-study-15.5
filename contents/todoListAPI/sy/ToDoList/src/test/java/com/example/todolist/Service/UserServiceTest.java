package com.example.todolist.Service;

import com.example.todolist.DTO.User.AddUserReqDTO;
import com.example.todolist.DTO.User.ReadUserResDTO;
import com.example.todolist.Exception.CommondException;
import com.example.todolist.Exception.ExceptionCode;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.domain.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Log4j2
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private AddUserReqDTO addUserReqDTO;

    @BeforeEach
    void beforeEach(){
        this.addUserReqDTO = AddUserReqDTO.builder()
                .email("test@naver.com")
                .name("testName")
                .password("1234")
                .build();

        this.user = User.builder()
                .email("test@naver.com")
                .name("testName")
                .password("1234")
                .build();
    }

    @Test
    @DisplayName("유저 회원가입 성공")
    public void addUserTest1(){
        //given
        given(userRepository.existsByEmail(any())).willReturn(false);

        //when
        userService.addUser(addUserReqDTO);
        User user = addUserReqDTO.toEntity(addUserReqDTO);

        //then
        assertThat(user.getEmail()).isEqualTo("test@naver.com");
        assertThat(user.getName()).isEqualTo("testName");
        assertThat(user.getPassword()).isEqualTo("1234");
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("유저 회원가입 실패, 중복된 이메일")
    public void addUserTest2(){
        //given
        given(userRepository.existsByEmail(any())).willReturn(true);

        //when,then
        CommondException exception = assertThrows(CommondException.class, () -> {
            userService.addUser(addUserReqDTO);
        });

        //then
        assertThat(exception.getExceptionCode().getMessage()).isEqualTo("중복된 이메일입니다.");
    }

    @Test
    @DisplayName("유저 정보 조회 성공")
    public void readUserTest1(){
        //given
        given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));

        //when
        ReadUserResDTO readUserResDTO = userService.readUserInfo(3l);

        //then
        Assertions.assertEquals(readUserResDTO.getEmail(),"test@naver.com");
        Assertions.assertEquals(readUserResDTO.getName(),"testName");
    }

    @Test
    @DisplayName("유저 정보 조회 실패, 없는 회원인 경우")
    public void readUserTest2(){
        //given
        given(userRepository.findById(any())).willThrow(new CommondException(ExceptionCode.USER_NOTFOUND));

        //when,then
        CommondException exception = assertThrows(CommondException.class, () -> {
            userService.readUserInfo(any());
        });

        //then
        assertThat(exception.getExceptionCode().getMessage()).isEqualTo("존재하지 않는 유저입니다");

    }
}
