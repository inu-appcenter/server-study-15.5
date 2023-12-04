package com.example.mytodolist.repository;

import com.example.mytodolist.Repository.TodoRepository;
import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("데이터베이스에 Todo 데이터를 저장하는 테스트")
    void todoRepositorySaveTest(){

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

        userRepository.save(givenUser);

        //when
        Todo savedTodo = todoRepository.save(givenTodo);

        //then
        Assertions.assertEquals(givenTodo.getTitle(),savedTodo.getTitle());
        Assertions.assertEquals(givenTodo.getContents(),savedTodo.getContents());
        Assertions.assertEquals(givenTodo.getCompleted(),savedTodo.getCompleted());
        Assertions.assertEquals(givenTodo.getDeadLine(),savedTodo.getDeadLine());
        Assertions.assertEquals(givenTodo.getUser(),savedTodo.getUser());
    }

    @Test
    @DisplayName("데이터베이스에서 Todo 데이터를 조회하는 테스트")
    void todoRepositorySelectTest(){

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

        userRepository.save(givenUser);
        todoRepository.save(givenTodo);

        //when
        Todo foundTodo = todoRepository.findById(givenTodo.getId()).get();

        //then
        Assertions.assertEquals(givenTodo.getTitle(),foundTodo.getTitle());
        Assertions.assertEquals(givenTodo.getContents(),foundTodo.getContents());
        Assertions.assertEquals(givenTodo.getCompleted(),foundTodo.getCompleted());
        Assertions.assertEquals(givenTodo.getDeadLine(),foundTodo.getDeadLine());
        Assertions.assertEquals(givenTodo.getUser(),foundTodo.getUser());
    }


    //find,save 리포지토리 테스트가 통과 한 후 라면, 현재 나의 로직에서 UPDATE 테스트를 해야 할 이유가 분명하지 않다.
//    @Test
//    @DisplayName("데이터베이스에서 Todo 데이터를 업데이트 하는 테스트")
//    void todoRepositoryUpdateTest(){
//    }

    @Test
    @DisplayName("데이터베이스에서 Todo 데이터를 삭제하는 테스트")
    void todoRepositoryDeleteTest()
    {
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

        userRepository.save(givenUser);
        Todo savedTodo= todoRepository.save(givenTodo);

        //when
        todoRepository.deleteById(savedTodo.getId());
        //then
        Assertions.assertFalse(todoRepository.existsById(savedTodo.getId()));
    }


}
