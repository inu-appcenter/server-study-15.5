package com.example.todolist.Controller;

import com.example.todolist.DTO.User.AddUserReqDTO;
import com.example.todolist.DTO.User.ChangeUserReqDTO;
import com.example.todolist.DTO.User.ReadUserResDTO;
import com.example.todolist.Exception.CommondException;
import com.example.todolist.Exception.ExceptionCode;
import com.example.todolist.Service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private ReadUserResDTO readUserResDTO;

    @BeforeEach
    void beforeEach(){
        readUserResDTO = ReadUserResDTO.builder()
                .email("test@google.com")
                .name("testName")
                .build();
    }

    @Test
    @DisplayName("유저 회원가입 성공")
    public void addUserTest1() throws Exception {
        //given
        AddUserReqDTO addUserReqDTO = AddUserReqDTO.builder()
                .email("string@naver.com")
                .name("testname")
                .password("1234")
                .build();

        Gson gson = new Gson();
        String requestBody = gson.toJson(addUserReqDTO);

        //when,then
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"))
                .andDo(print());

        verify(userService).addUser(any(AddUserReqDTO.class));
    }
    @Test
    @DisplayName("유저 회원가입 email유효성 검사 실패")
    public void addUserTest2() throws Exception {
        //given
        AddUserReqDTO addUserReqDTO = AddUserReqDTO.builder()
                .email("string")
                .name("testname")
                .password("1234")
                .build();

        Gson gson = new Gson();
        String requestBody = gson.toJson(addUserReqDTO);

        //when,then
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.email").value("must be a well-formed email address"))
                .andDo(print());
    }

    @Test
    @DisplayName("유저 회원가입 notNull유효성 검사 실패")
    public void addUserTest3() throws Exception {
        //given
        AddUserReqDTO addUserReqDTO = AddUserReqDTO.builder()
                .email("string")
                .password("1234")
                .build();

        Gson gson = new Gson();
        String requestBody = gson.toJson(addUserReqDTO);

        //when,then
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.name").value("must not be blank"))
                .andExpect(jsonPath("$.data.email").value("must be a well-formed email address"))
                .andDo(print());
    }

    @Test
    @DisplayName("유저 회원가입, email중복 예외")
    public void addUserTest4() throws Exception {
        //given
        AddUserReqDTO addUserReqDTO = AddUserReqDTO.builder()
                .email("string@naver.com")
                .name("testname")
                .password("1234")
                .build();

        Gson gson = new Gson();
        String requestBody = gson.toJson(addUserReqDTO);
        doThrow(new CommondException(ExceptionCode.EMAIL_DUPLICATED)).when(userService).addUser(any());

        //when,then
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("중복된 이메일입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("유저 조회 성공")
    public void readUserTest1() throws Exception {

        //given
        given(userService.readUserInfo(any())).willReturn(this.readUserResDTO);

        //when,then
        this.mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("test@google.com"))
                .andExpect(jsonPath("$.data.name").value("testName"))
                .andDo(print());
        //then
        verify(userService).readUserInfo(any());
    }

    @Test
    @DisplayName("유저 조회 실패, 없는 회원예외")
    public void readUserTest2() throws Exception {

        //given
        given(userService.readUserInfo(any())).willThrow(new CommondException(ExceptionCode.USER_NOTFOUND));

        //when,then
        this.mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("존재하지 않는 유저입니다"))
                .andDo(print());

        //then
        verify(userService).readUserInfo(any());
    }

    @Test
    @DisplayName("유저 정보 변경성공")
    public void changeUserTest1() throws Exception{
        //given
        ChangeUserReqDTO changeUserReqDTO = ChangeUserReqDTO.builder()
                .name("string")
                .oldPassword("1234")
                .newPassword("12345")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(changeUserReqDTO);

        //when,then
        this.mockMvc.perform(patch("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("유저정보 변경성공"));

        // then
        verify(userService).changeUserInfo(any(ChangeUserReqDTO.class));
    }

    @Test
    @DisplayName("유저 정보 변경 유효성검사실패")
    public void changeUserTest2() throws Exception{
        //given
        ChangeUserReqDTO changeUserReqDTO = ChangeUserReqDTO.builder()
                .name("string")
                .newPassword("12345")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(changeUserReqDTO);

        //when,then
        this.mockMvc.perform(patch("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.oldPassword").value("must not be blank"));
    }

    @Test
    @DisplayName("유저 정보 변경, 비밀번호 불일치")
    public  void changeUserTest3() throws Exception{
        //given
        ChangeUserReqDTO changeUserReqDTO = ChangeUserReqDTO.builder()
                .name("string")
                .oldPassword("1234")
                .newPassword("12345")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(changeUserReqDTO);

        doThrow(new CommondException(ExceptionCode.PASSWORD_NOTMATCH)).when(userService).changeUserInfo(any());

        //when, then
        this.mockMvc.perform(patch("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("비밀번호가 일치하지 않습니다"));
    }
}
