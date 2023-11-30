package com.example.todo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setupData() {
        user = User.builder()
                .loginId("namu1234@gmail.com")
                .password("test123")
                .name("namu")
                .build();
    }

    @Test
    @DisplayName("원본 유저와 저장된 유저의 내용이 같아야 한다.")
    public void savedUser_is_same_with_originalUser() {
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(savedUser.getLoginId()).isEqualTo(user.getLoginId());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getName()).isEqualTo(user.getName());
    }

}