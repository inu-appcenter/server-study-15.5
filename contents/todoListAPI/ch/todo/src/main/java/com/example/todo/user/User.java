package com.example.todo.user;

import com.example.todo.task.Task;
import com.example.todo.user.dto.UserRequestDto;
import com.example.todo.user.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "user_tb")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

//    @Column(unique = true, name = "login_id")
    @Column(name = "login_id")
    private String loginId;

    private String password;

    private String name;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<>();

    @Builder
    public User(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    public void assignRoles(List<Authority> roles) {
        this.roles = roles.stream().map(authority -> {
            authority.assignUser(this);
            return authority;
        }).collect(Collectors.toList());
    }

    public UserResponseDto toResponseDto() {
        return UserResponseDto.builder()
                .userId(this.userId)
                .name(this.name)
                .loginId(this.loginId)
                .password(this.password)
                .build();
    }


    public User updateFromDto(UserRequestDto userRequestDto) {
        this.loginId = userRequestDto.getLoginId();
        this.password = userRequestDto.getPassword();
        this.name = userRequestDto.getName();
        return this;
    }

}
