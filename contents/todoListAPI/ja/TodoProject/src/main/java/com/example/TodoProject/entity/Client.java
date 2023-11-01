package com.example.TodoProject.entity;

import com.example.TodoProject.common.Time;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Client_tb")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_num")
    private Long clientNum;

    @Column(name = "client_id")
    private String clientId;

    @Column(name="client_password")
    private String clientPassword;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "client_role")
    private String clientRole;

    @Column(nullable = false, name = "client_phone_num")
    private String clientPhoneNum;

    @Builder.Default
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Todo> todoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<TodoGroup> todoGroup = new ArrayList<>();


}
