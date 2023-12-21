package com.example.todo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
2023.12.21 jang-namu
User가 갖는 권한에 대한 클래스이다.
name 필드는 권한을 나타내는 문자열이다. (Admin, User 등..)
추후 권한이 세분화됨에 따라 한 사용자가 여러 권한을 갖을 수 있도록 ManyToOne으로 연관관계를 설정한다.
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="authority_id")
    private Long authorityId;

    private String name;

    @JoinColumn(name="user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Authority(String name) {
        this.name = name;
    }

    public void assignUser(User user) { this.user = user; }
}
