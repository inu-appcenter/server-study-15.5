package com.example.todo.user;

import com.example.todo.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserController.class})  // @WebMvcTest - DefaultCacheAwareContextLoaderDelegate.java
@AutoConfigureMockMvc(addFilters = false) // 401 Unautohrized Error 해결 임시방편? https://lemontia.tistory.com/1088
@ActiveProfiles("testH2")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    /*
    단위테스트나 통합테스트의 책임과는 무관하게
    @Transactional: 이상하게 Controller에서는 실제 데이터베이스에 작성되는 반면,
    Service Layer의 테스트에서는 작성되지 않았다.
    -> @DataJpaTest에는 기본적으로 @Transactional 어노테이션이 포함된다.
    따라서 테스트 메서드가 종료되면서 롤백되기 때문이다!
     */
    @Test
    void getUser() throws Exception {
        Long userId = 1L;
        UserResponseDto mockUserResponseDto = UserResponseDto.builder()
                .userId(userId)
                .loginId("namu1234@gmail.com")
                .password("test123")
                .name("namu")
                .build();

        given(userService.getUser(userId)).willReturn(mockUserResponseDto);

        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.intValue()))
                .andExpect(jsonPath("$.loginId").value("namu1234@gmail.com"))
                .andExpect(jsonPath("$.password").value("test123"))
                .andExpect(jsonPath("$.name").value("namu"));
    }

}