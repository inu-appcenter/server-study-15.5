package com.example.mytodolist.service;

import com.example.mytodolist.Repository.TodoRepository;
import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.TodoRequestDto;
import com.example.mytodolist.dto.TodoResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@Import({TodoService.class})
public class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("given_when_then 방식으로 getTodo 테스트")
    void getTodoTest(){

        //given
        Todo givenTodo = Todo.builder()
                .title("내일할일")
                .contents("밥먹기")
                .deadLine(LocalDateTime.of(2023,12,31,00,00,00))
                .build();

        Mockito.when(todoRepository.findById(1L))
                .thenReturn(Optional.of(givenTodo));

        //when
        TodoResponseDto todoResponseDto = todoService.getTodo(1L);

        //then
        Assertions.assertEquals(todoResponseDto.getId(),givenTodo.getId());
        Assertions.assertEquals(todoResponseDto.getTitle(),givenTodo.getTitle());
        Assertions.assertEquals(todoResponseDto.getContent(),todoResponseDto.getContent());
        Assertions.assertEquals(todoResponseDto.getCompleted(),givenTodo.getCompleted());
        Assertions.assertEquals(todoResponseDto.getDeadLine(),givenTodo.getDeadLine());

        verify(todoRepository).findById(1L);
    }


    @Test
    @DisplayName("given_when_then 방식으로 saveTodo 테스트")
    void saveTodoTest(){

        //given
        LocalDateTime deadLine = LocalDateTime.of(2023,12,31,00,00,00);

        User givenUser = User.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        Todo givenTodo = Todo.builder()
                .title("내일할일")
                .contents("밥먹기")
                .deadLine(deadLine)
                .user(givenUser)
                .build();

        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("내일할일")
                .content("밥먹기")
                .deadLine("2023-12-31T00:00:00")
                .build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(givenUser));
        Mockito.when(todoRepository.save(any(Todo.class))).then(returnsFirstArg());

        //when
        TodoResponseDto todoResponseDto = todoService.saveTodo(1L,todoRequestDto);

        //then
        Assertions.assertEquals(todoResponseDto.getTitle(),"내일할일");
        Assertions.assertEquals(todoResponseDto.getContent(),"밥먹기");
        Assertions.assertEquals(todoResponseDto.getCompleted(),false);
        Assertions.assertEquals(todoResponseDto.getDeadLine(),deadLine);

        verify(todoRepository).save(any());
    }

    @Test
    @DisplayName("given_when_then 방식으로 updateTodo 테스트")
    void updateTodoTest(){

        //given
        LocalDateTime deadLine = LocalDateTime.of(2023,12,31,00,00,00);
        LocalDateTime updateTime = LocalDateTime.of(2023,11,30,23,59,59);


        Todo givenTodo = Todo.builder()
                .title("내일할일")
                .contents("밥먹기")
                .deadLine(deadLine)
                .build();

        TodoRequestDto todoRequestDto = TodoRequestDto.builder()
                .title("오늘할일")
                .content("잠자기")
                .deadLine("2023-11-30T23:59:59")
                .build();

        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.of(givenTodo));
        Mockito.when(todoRepository.save(any(Todo.class))).then(returnsFirstArg());

        //when
        TodoResponseDto todoResponseDto = todoService.updateTodo(1L,todoRequestDto);

        //then
        Assertions.assertEquals(todoResponseDto.getTitle(),"오늘할일");
        Assertions.assertEquals(todoResponseDto.getContent(),"잠자기");
        Assertions.assertEquals(todoResponseDto.getCompleted(),false);
        Assertions.assertEquals(todoResponseDto.getDeadLine(),updateTime);

        verify(todoRepository).findById(1L);
        verify(todoRepository).save(any());
    }

    @Test
    @DisplayName("deleteTodo 테스트")
    void deleteTodoTest(){
        todoService.deleteTodo(1L);
        verify(todoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("checkCompleted 테스트")
    void checkCompletedTest(){

        //given
        LocalDateTime deadLine = LocalDateTime.of(2023,12,31,00,00,00);

        User givenUser = User.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        Todo givenTodo = Todo.builder()
                .title("내일할일")
                .contents("밥먹기")
                .deadLine(deadLine)
                .user(givenUser)
                .build();

        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.of(givenTodo));

        //when
        TodoResponseDto todoResponseDto = todoService.checkCompleted(1L,true);

        //then
        Assertions.assertEquals(todoResponseDto.getTitle(),"내일할일");
        Assertions.assertEquals(todoResponseDto.getContent(),"밥먹기");
        Assertions.assertEquals(todoResponseDto.getCompleted(),true);
        Assertions.assertEquals(todoResponseDto.getDeadLine(),deadLine);

        verify(todoRepository).findById(1L);
        verify(todoRepository).save(givenTodo);
        verify(userRepository).save(givenUser);

    }
}
