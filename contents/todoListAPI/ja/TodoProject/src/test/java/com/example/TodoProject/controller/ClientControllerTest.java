package com.example.TodoProject.controller;


import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.CommonResponseDto;
import com.example.TodoProject.service.ClientService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.example.TodoProject.dto.Client.ClientRequestDto.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ClientControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private ClientService clientService;
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    @DisplayName("유저 회원가입 테스트")
    void signupClient() throws Exception {
//        List<String> role_user = new ArrayList<>();
//        role_user.add("ROLE_USER");
//        RequestClientDto requestClientDto = RequestClientDto.builder()
//                .clientId("matchingA")
//                .clientPassword("password134@")
//                .clientName("개굴이")
//                .clientEmail("inu1324@naver.com")
//                .clientPhoneNum("010-1236-9839")
//                .clientRole(role_user)
//                .build();
//
//        doNothing().when(clientService).signUp(requestClientDto);
//
//        ResponseEntity<CommonResponseDto> responseEntity = ResponseEntity.ok().body(new CommonResponseDto(CommonResponse.SUCCESS, "회원가입 성공", null));
//        CommonResponseDto responseBody = responseEntity.getBody();
//
//        mvc.perform(post("/clients/sign-up")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestClientDto)))
//                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("유저 회원탈퇴 테스트")
    void editClient() throws Exception {
//        List<String> role_user = new ArrayList<>();
//        role_user.add("ROLE_USER");
//        RequestClientDto requestClientDto = RequestClientDto.builder()
//                .clientId("matchingA")
//                .clientPassword("password134@")
//                .clientName("개굴이")
//                .clientEmail("inu1324@naver.com")
//                .clientPhoneNum("010-1236-9839")
//                .clientRole(role_user)
//                .build();
//        doNothing().when(clientService).signUp(requestClientDto);
//        Long clientNum = 1L;
//
//        mvc.perform(delete("/clients/{clientNum}", clientNum)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestClientDto)))
//                .andExpect(status().isOk());
    }


}
