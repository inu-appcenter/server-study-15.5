package com.example.mytodolist.controller;

import com.example.mytodolist.dto.TodoResponseDto;
import com.example.mytodolist.dto.UserRequestDto;
import com.example.mytodolist.dto.UserResponseDto;
import com.example.mytodolist.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserControllerTest.class)
@Import(UserController.class) //UserController 클래스가 다른 컴포넌트들과 연관이 있고, 특정 빈 설정이 필요하다면 @Import를 사용하여 해당 설정을 가져올 수 있ek
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("MockMvc를 통한 User 데이터 가져오기")
    void getUserTest() throws Exception{

        //given
        given(userService.getUser("USER")).willReturn(UserResponseDto.builder()
                .id(1L)
                .name("홍길순")
                .email("popora99@naver.com")
                .level(0)
                .build());

        //when
        mockMvc.perform(get("/users/{id}",1))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.level").exists())
                .andDo(print());

        verify(userService).getUser("USER");
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("User 데이터 생성 테스트")
    void createUserTest() throws Exception{

        //given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        given(userService.saveUser(any(UserRequestDto.class))) //Mockito에서 메서드 호출시 특정한 인자가 전달되는지 여부만을 검증
                .willReturn(UserResponseDto.builder()
                .id(1L)
                .name("홍길순")
                .email("popora99@naver.com")
                .level(0)
                .build());


        Gson gson = new Gson();
        String content = gson.toJson(userRequestDto);

        //when
        mockMvc.perform(post("/users")
                        .content(content).contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.level").exists())
                .andDo(print());

        verify(userService).saveUser(any(UserRequestDto.class)); //Mockito에서 메서드 호출시 특정한 인자가 전달되는지 여부만을 검증
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("User 데이터 수정 테스트")
    void changeUserTest() throws Exception{

        //given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("업데이트홍길순")
                .email("popora99@gmail.net")
                .build();

        given(userService.updateUser(eq("USER"),any(UserRequestDto.class))) //Mockito에서 메서드 호출시 특정한 인자가 전달되는지 여부만을 검증
                .willReturn(UserResponseDto.builder()
                        .id(1L)
                        .name("업데이트홍길순")
                        .email("popora99@gmail.net")
                        .level(0)
                        .build());


        Gson gson = new Gson();
        String content = gson.toJson(userRequestDto);

       //when
        mockMvc.perform(put("/users/{id}",1L)
                        .content(content).contentType(MediaType.APPLICATION_JSON))
       //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.level").exists())
                .andDo(print());

        verify(userService).updateUser(eq("USER"),any(UserRequestDto.class)); //Mockito에서 메서드 호출시 특정한 인자가 전달되는지 여부만을 검증
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("User 데이터 삭제 테스트")
    void deleteUserTest() throws Exception{

        //when
        mockMvc.perform(delete("/users/{id}",1L))
        //then
                        .andExpect(status().isOk())
                                .andDo(print());

        verify(userService).deleteUser("USER");
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("User의 게시물들을 조회 테스트")
    void getTodoListTest()throws Exception{

        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023,12,31,00,00,00);

        List<TodoResponseDto> todoList = Collections.singletonList(TodoResponseDto.builder()
                        .id(1L)
                        .title("나의 하루")
                        .content("1.먹기,2.자기")
                        .completed(false)
                        .deadLine(localDateTime)
                        .build());


        given(userService.getTodosByUserId("USER")).willReturn(todoList);

        //when
        mockMvc.perform(get("/users/search/{id}",1L))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].title").exists())
                .andExpect(jsonPath("$.[0].content").exists())
                .andExpect(jsonPath("$.[0].completed").exists())
                .andExpect(jsonPath("$.[0].deadLine").exists())
                .andDo(print());

        verify(userService).getTodosByUserId("USER");
    }
}
