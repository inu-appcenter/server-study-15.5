package com.example.TodoProject.controller;

import com.example.TodoProject.service.ClientService;
import com.example.TodoProject.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(TodoController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class TodoControllerTest {

    //투두 제작
    //투두 수정
    //투두 삭제
    //투두 검색
    //투두 그룹이 없는 투두 전체 조회
    @Autowired
    MockMvc mvc;

    @MockBean
    private TodoService todoService;


    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("투두 작성")
    void createTodoTest(){

    }

    @Test
    @DisplayName("투두 수정")
    void editTodoTest(){

    }

    @Test
    @DisplayName("투두 삭제")
    void deleteTodoTest(){

    }

    @Test
    @DisplayName("투두 검색")
    void searchTodoTest(){

    }

    @Test
    @DisplayName("투두 그룹이 없는 투두 전체 조회")
    void getAllTodoTest(){

    }
}
