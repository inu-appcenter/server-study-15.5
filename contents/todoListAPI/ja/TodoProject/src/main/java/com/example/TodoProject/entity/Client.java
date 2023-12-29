package com.example.TodoProject.entity;

import com.example.TodoProject.common.Time;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static com.example.TodoProject.dto.Client.ClientRequestDto.*;
import static com.example.TodoProject.dto.Client.ClientResponseDto.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "client_tb")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends Time implements UserDetails {

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "client_role")
    private List<String> clientRole;

    @Column(nullable = false, name = "client_phone_num")
    private String clientPhoneNum;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Todo> todo = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<TodoGroup> todoGroup = new ArrayList<>();

    public void editUser(EditClientDto editClientDto){
        this.clientPassword = editClientDto.getClientPassword();
        this.clientEmail = editClientDto.getClientEmail();
        this.clientPhoneNum = editClientDto.getClientPhoneNum();
    }

    @Builder
    public Client(String clientId, String clientPassword, String clientName, String clientEmail, List<String> clientRole, String clientPhoneNum){
        this.clientId = clientId;
        this.clientPassword = clientPassword;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientRole = clientRole;
        this.clientPhoneNum=clientPhoneNum;
    }

    public ResponseClientDto toDto(){
        return ResponseClientDto.builder()
                .clientId(clientId)
                .clientEmail(clientEmail)
                .clientName(clientName)
                .clientRole(clientRole)
                .clientPassword(clientPassword)
                .clientPhoneNum(clientPhoneNum)
                .clientNum(clientNum)
                .build();
    }

    //userDetails 구성요소

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.clientRole.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    @Override
    public String getUsername() {  // 계정의 이름을 리턴, 일반적으로 id
        return this.clientId;
    }

    @Override
    public boolean isAccountNonExpired() {  // 계정이 만료되었는지 리턴, true는 만료 되지 않음.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {  // 계정이 잠겨있는지 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {   // 비밀번호가 만료되었는지 여부
        return true;
    }

    @Override
    public boolean isEnabled() {    // 계정이 활성회 되어있는지 여부
        return true;
    }

    @Override
    public String getPassword() {
        return this.clientPassword;
    }
}
