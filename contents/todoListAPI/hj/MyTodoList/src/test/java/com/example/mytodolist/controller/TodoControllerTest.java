package com.example.mytodolist.controller;

import com.example.mytodolist.dto.TodoRequestDto;
import com.example.mytodolist.dto.TodoResponseDto;
import com.example.mytodolist.service.TodoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoControllerTest.class)
@Import(TodoController.class) //UserController 클래스가 다른 컴포넌트들과 연관이 있고, 특정 빈 설정이 필요하다면 @Import를 사용하여 해당 설정을 가져올 수 있다.
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TodoService todoService;

    @Test
    @DisplayName("MockMvc를 통한 Todo 데이터 가져오기")
    void getTodoTest() throws Exception{

        //given
        LocalDateTime deadLine = LocalDateTime.of(2023,12,31,10,10,10);
        LocalDateTime time = LocalDateTime.now();
        given(todoService.getTodo(1L)).willReturn(TodoResponseDto.builder()
                .id(1L)
                .title("내일 할일")
                .content("1.밥먹기, 2.잠자기")
                .completed(false)
                .deadLine(deadLine)
                .createdDate(time)
                .modifiedDate(time)
                .build());

        //when
        mockMvc.perform(get("/todos/{id}",1))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.completed").exists())
                .andExpect(jsonPath("$.deadLine").exists())
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.modifiedDate").exists())
                .andDo(print());

        verify(todoService).getTodo(1L);
    }

    @Test
    @WithMockUser(username = "USER",password = "USER")
    @DisplayName("Todo 데이터 생성 테스트")
    void createTodoTest() throws Exception{

        //given
        LocalDateTime deadLine = LocalDateTime.of(2023,12,31,10,10,10);
        LocalDateTime time = LocalDateTime.now();

        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("내일 할일")
                .content("1.밥먹기, 2.잠자기")
                .deadLine("2023-12-31T23:59:59")
                .build();

        given(todoService.saveTodo(eq("USER"), any(TodoRequestDto.class))) //Mockito에서 메서드 호출시 특정한 인자가 전달되는지 여부만을 검증
                .willReturn(TodoResponseDto.builder()
                        .id(1L)
                        .title("내일 할일")
                        .content("1.밥먹기, 2.잠자기")
                        .completed(false)
                        .deadLine(deadLine)
                        .createdDate(time)
                        .modifiedDate(time)
                        .build());


        Gson gson = new Gson();
        String content = gson.toJson(todoRequestDto);

        //when
        mockMvc.perform(post("/todos/{id}",1L)
                        .content(content).contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.completed").exists())
                .andExpect(jsonPath("$.deadLine").exists())
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.modifiedDate").exists())
                .andDo(print());

        verify(todoService).saveTodo(eq("USER"),any(TodoRequestDto.class)); //Mockito에서 메서드 호출시 특정한 인자가 전달되는지 여부만을 검증
    }

    @Test
    @DisplayName("Todo 데이터 수정 테스트")
    void changeTodoTest() throws Exception{

        //given
        LocalDateTime deadLine = LocalDateTime.of(2024,12,31,00,00,00);
        LocalDateTime time = LocalDateTime.now();

        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("내일 아니 할 일")
                .content("1.똥 먹기, 2.밥 싸기")
                .deadLine("2024-12-31T00:00:00")
                .build();

        given(todoService.updateTodo(eq(1L),any(TodoRequestDto.class))) //Mockito에서 메서드 호출시 특정한 인자가 전달되는지 여부만을 검증
                .willReturn(TodoResponseDto.builder()
                        .id(1L)
                        .title("내일 아니 할 일")
                        .content("1.똥 먹기, 2.밥 싸기")
                        .completed(false)
                        .deadLine(deadLine)
                        .modifiedDate(time)
                        .createdDate(time)
                        .build());


        Gson gson = new Gson();
        String content = gson.toJson(todoRequestDto);

        //when
        mockMvc.perform(put("/todos/{id}",1L)
                        .content(content).contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.completed").exists())
                .andExpect(jsonPath("$.deadLine").exists())
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.modifiedDate").exists())
                .andDo(print());

        verify(todoService).updateTodo(eq(1L),any(TodoRequestDto.class)); //Mockito에서 메서드 호출시 특정한 인자가 전달되는지 여부만을 검증
    }

    @Test
    @DisplayName("Todo 데이터 삭제 테스트")
    void deleteTodoTest() throws Exception{

        //when
        mockMvc.perform(delete("/todos/{id}",1L))
        //than
                .andExpect(status().isOk())
                .andDo(print());

        verify(todoService).deleteTodo(1L);
    }


    @Test
    @DisplayName("Todo 성공여부 체크 테스트")
    void checkCompletedTodoTest() throws Exception{

        //given
        LocalDateTime deadLine = LocalDateTime.of(2024,12,31,00,00,00);
        LocalDateTime time = LocalDateTime.now();

        given(todoService.checkCompleted(1L,true)).willReturn(TodoResponseDto.builder()
                .id(1L)
                .title("내일 할일")
                .content("1.밥먹기, 2.잠자기")
                .completed(true)
                .deadLine(deadLine)
                .createdDate(time)
                .modifiedDate(time)
                .build());


        //when
        mockMvc.perform(put("/todos/check")
                        .param("id","1").param("completed","true"))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.completed").exists())
                .andExpect(jsonPath("$.deadLine").exists())
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.modifiedDate").exists())
                .andDo(print());

        verify(todoService).checkCompleted(1L,true);
    }



}
