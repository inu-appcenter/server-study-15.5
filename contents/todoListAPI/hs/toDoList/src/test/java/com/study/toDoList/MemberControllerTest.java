package com.study.toDoList;

import com.google.gson.Gson;
import com.study.toDoList.controller.MemberController;
import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import com.study.toDoList.dto.MemberResponseDto;
import com.study.toDoList.dto.MemberSaveDto;
import com.study.toDoList.dto.MemberUpdateDto;
import com.study.toDoList.dto.TaskListResponseDto;
import com.study.toDoList.repositoy.MemberRepository;
import com.study.toDoList.service.MemberService;
import com.study.toDoList.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    TaskService taskService;

    @MockBean
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 가져오기 테스트")
    void getMemberTest() throws Exception{
        given(memberService.getMember(123L)).willReturn(new MemberResponseDto(Member.builder().email("test@gmail.com").password("1234").nickname("test").build()));

        String memberId = "123";

        mockMvc.perform(
                get("/api/members/"+memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.nickname").value("test"))
                .andDo(print());

        verify(memberService).getMember(123L);
    }


    @Test
    @DisplayName("회원 가입 테스트")
    void joinTest() throws Exception {

        given(memberService.save(MemberSaveDto.builder().email("test@email.com").password("1234").nickname("test").build())).willReturn(1L);

        MemberSaveDto memberSaveDto = MemberSaveDto.builder().email("test@email.com").password("1234").nickname("test").build();

        Gson gson = new Gson();
        String content = gson.toJson(memberSaveDto);

        mockMvc.perform(
                post("/api/members/").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.msg").exists())
                .andDo(print());

        verify(memberService).save(any());
    }


    @Test
    @DisplayName("회원 수정 테스트")
    void updateTest() throws Exception{
        MemberUpdateDto memberUpdateDto = MemberUpdateDto.builder().password("123").nickname("test1").build();
        given(memberService.update(123L, MemberUpdateDto.builder().password("123").nickname("test1").build())).willReturn(123L);
        Long memberId = 123L;

        Gson gson = new Gson();
        String content = gson.toJson(memberUpdateDto);
        mockMvc.perform(
                put("/api/members/"+memberId).contentType(MediaType.APPLICATION_JSON).content(content)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.msg").exists())
                .andDo(print());
        verify(memberService).update(any(),any());
    }
    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteTest() throws Exception{

        Long memberId = 123L;
        mockMvc.perform(
                delete("/api/members/"+memberId)
        )
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.msg").exists())
                .andDo(print());
        verify(memberService).delete(any());
    }

    @Test
    @DisplayName("회원의 모든 할일 가져오기 테스트")
    void getAllTask() throws Exception{
        Member member = Member.builder().email("test@email.com").password("1234").nickname("test").build();
        when(memberRepository.findById(123L)).thenReturn(Optional.of(member));


        LocalDate localDate = LocalDate.of(2044,11,11);
        Task task1 = Task.builder().title("test1").description("test1").member(member).endDate(localDate).build();
        Task task2 = Task.builder().title("test2").description("test2").member(member).endDate(localDate).build();
        List<TaskListResponseDto> taskListResponseDtos = Arrays.asList(new TaskListResponseDto(task1),new TaskListResponseDto(task2));
        when(taskService.getAllTask(123L)).thenReturn(taskListResponseDtos);

        List<TaskListResponseDto> dtos = taskService.getAllTask(123L);

        Long memberId = 123L;
        mockMvc.perform(
                get("/api/members/tasks/"+memberId)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[1].title").exists())
                .andDo(print());
        //verify(taskService).getAllTask(any());
    }

}
