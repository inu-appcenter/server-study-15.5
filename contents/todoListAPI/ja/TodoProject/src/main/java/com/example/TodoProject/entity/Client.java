package com.example.TodoProject.entity;

import com.example.TodoProject.common.Time;
import jakarta.persistence.*;
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
    private Long clientNum;

    private String clientId;

    private String clientPassword;

    private String clientName;

    private String clientEmail;

    private String clientRole;

    @Column(nullable = false)
    private String clientPhoneNum;

    @Builder.Default
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Todo> todoList = new ArrayList<>();

    @Builder
    public Client(Long clientNum, String clientId, String clientPassword, String clientName, String clientEmail, String clientRole,String clientPhoneNum){
      this.clientNum = clientNum;
      this.clientId = clientId;
      this.clientPassword = clientPassword;
      this.clientName = clientName;
      this.clientEmail = clientEmail;
      this.clientRole = clientRole;
      this.clientPhoneNum=clientPhoneNum;
    }
}
