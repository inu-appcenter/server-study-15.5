package com.example.TodoProject.entity;

import com.example.TodoProject.common.Time;
import javax.persistence.*;

import com.example.TodoProject.dto.ShortClientDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "client_tb")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    private List<Todo> todo = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<TodoGroup> todoGroup = new ArrayList<>();

    public void editUser(ShortClientDto shortClientDto){
        this.clientPassword = shortClientDto.getClientPassword();
        this.clientEmail = shortClientDto.getClientEmail();
        this.clientPhoneNum = shortClientDto.getClientPhoneNum();
    }

}
