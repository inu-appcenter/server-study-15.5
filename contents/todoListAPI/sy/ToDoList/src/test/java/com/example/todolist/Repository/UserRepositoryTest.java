package com.example.todolist.Repository;

import com.example.todolist.Exception.CommondException;
import com.example.todolist.Exception.ExceptionCode;
import com.example.todolist.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;
    @BeforeEach
    void beforeEach(){
        user = User.builder()
                .email("test@naver.com")
                .name("testName")
                .password("1234")
                .build();
    }

    @Test
    @DisplayName("회원 저장 성공")
    public void addUserTest(){

        //when
        User user = userRepository.save(this.user);

        //then
        assertThat(user.getEmail()).isEqualTo("test@naver.com");
        assertThat(user.getName()).isEqualTo("testName");
        assertThat(user.getPassword()).isEqualTo("1234");

    }

    @Test
    @DisplayName("회원 존재여부 쿼리성공")
    public void existUserTest(){
        //given
        userRepository.save(this.user);

        //when
        boolean notExistTest = userRepository.existsByEmail("test1@naver.com");
        boolean existTest = userRepository.existsByEmail("test@naver.com");

        //then
        assertThat(notExistTest).isFalse();
        assertThat(existTest).isTrue();
    }

    @Test
    @DisplayName("회원 조회 성공")
    public void readUserTest(){
        //given
        long userId = userRepository.save(this.user).getUserId();

        //when
        User user = userRepository.findById(userId).orElseThrow(()-> new CommondException(ExceptionCode.USER_NOTFOUND));

        //then
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getEmail()).isEqualTo("test@naver.com");
        assertThat(user.getName()).isEqualTo("testName");
        assertThat(user.getPassword()).isEqualTo("1234");
    }

    @Test
    @DisplayName("회원 update 성공")
    public void updateUserTest(){
        //given
        User user = userRepository.save(this.user);
        user.changeName("changedName");

        //when
        User changedUser = userRepository.save(user);

        //then
        assertThat(user.getUserId()).isEqualTo(user.getUserId());
        assertThat(user.getEmail()).isEqualTo("test@naver.com");
        assertThat(user.getName()).isEqualTo("changedName");
        assertThat(user.getPassword()).isEqualTo("1234");
    }

    @Test
    @DisplayName("회원 삭제 성공")
    public void deleteUserTest(){
        //given
        User user = userRepository.save(this.user);

        //when
        boolean beforeDelete = userRepository.existsById(user.getUserId());
        userRepository.delete(user);
        boolean afterDelete = userRepository.existsById(user.getUserId());

        //then
        assertThat(beforeDelete).isTrue();
        assertThat(afterDelete).isFalse();

    }

}
