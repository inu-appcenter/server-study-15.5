package com.example.TodoProject.Entity;

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
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long client_Num;

    private String client_id;

    private String client_Password;

    private String client_Name;

    private String client_Email;

    private String client_Role;

    @Column(nullable = false)
    private String client_PhoneNum;

    @Builder.Default
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Todo> todoList = new ArrayList<>();

    @Builder
    public Client(Long client_Num, String client_id, String client_Password, String client_Name, String client_Email, String client_Role,String client_PhoneNum){
      this.client_Num = client_Num;
      this.client_id = client_id;
      this.client_Password = client_Password;
      this.client_Name = client_Name;
      this.client_Email = client_Email;
      this.client_Role = client_Role;
      this.client_PhoneNum=client_PhoneNum;
    }
}
