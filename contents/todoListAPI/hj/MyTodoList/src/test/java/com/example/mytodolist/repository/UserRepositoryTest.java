package com.example.mytodolist.repository;

import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("데이터베이스에 유저데이터를 저장하는 테스트")
    void userRepositorySaveTest(){

        //given
        User givenUser = User.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        //when
        User savedUser = userRepository.save(givenUser);

        //then
        Assertions.assertEquals(givenUser.getName(),savedUser.getName());
        Assertions.assertEquals(givenUser.getEmail(),savedUser.getEmail());
        Assertions.assertEquals(givenUser.getLevel(),savedUser.getLevel());
    }

    @Test
    @DisplayName("데이터베이스에서 유저 데이터를 조회하는 테스트")
    void userRepositorySelectTest(){

        //given
        User givenUser = User.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        User savedUser = userRepository.save(givenUser);

        //when
        User foundUser = userRepository.findById(savedUser.getId()).get();

        //then
        Assertions.assertEquals(givenUser.getName(), foundUser.getName());
        Assertions.assertEquals(givenUser.getEmail(), foundUser.getEmail());
        Assertions.assertEquals(givenUser.getLevel(), foundUser.getLevel());
    }
    
    //find,save 리포지토리 테스트가 통과 한 후 라면, 현재 나의 로직에서 UPDATE 테스트를 해야 할 이유가 분명하지 않다.
//    @Test
//    @DisplayName("데이터베이스에서 유저 데이터를 업데이트 하는 테스트")
//    void userRepositoryUpdateTest(){
//    }

    @Test
    @DisplayName("데이터베이스에서 유저 데이터를 삭제하는 테스트")
    void userRepositoryDeleteTest()
    {
        //given
        User givenUser = User.builder()
                .name("홍길순")
                .email("popora99@naver.com")
                .build();

        User savedUser = userRepository.save(givenUser);

        //when
        userRepository.deleteById(savedUser.getId());

        //then
        Assertions.assertFalse(userRepository.existsById(savedUser.getId()));
    }


}
