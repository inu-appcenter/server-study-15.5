package com.example.TodoProject.repository;

import com.example.TodoProject.config.ex.NotFoundElementException;
import com.example.TodoProject.dto.Client.ClientRequestDto;
import com.example.TodoProject.entity.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.TodoProject.dto.Client.ClientRequestDto.*;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;


    @Test
    @DisplayName("회원가입이 잘 되는지(db에 유저가 잘 저장되는지) 확인")
    void saveClient(){
        List<String> role_user = new ArrayList<>();
        role_user.add("ROLE_USER");
        Client client = Client.builder()
                .clientId("inu1234")
                .clientPassword("password1234@")
                .clientRole(role_user)
                .clientName("깨구리")
                .clientEmail("inu1234@naver.com")
                .clientPhoneNum("010-1234-5678")
                .build();
        Client saveClient = clientRepository.save(client);

        Assertions.assertThat(client).isSameAs(saveClient);
        Assertions.assertThat(client.getClientId()).isEqualTo(saveClient.getClientId());
        Assertions.assertThat(client.getClientPassword()).isEqualTo(saveClient.getClientPassword());
        Assertions.assertThat(client.getClientRole()).isEqualTo(saveClient.getClientRole());
        Assertions.assertThat(client.getClientEmail()).isEqualTo(saveClient.getClientEmail());
        Assertions.assertThat(client.getClientName()).isEqualTo(saveClient.getClientName());
        Assertions.assertThat(client.getClientPhoneNum()).isEqualTo(saveClient.getClientPhoneNum());

    }

    @Test
    @DisplayName("유저 조회 테스트")
    void editClient(){
        List<String> role_user = new ArrayList<>();
        role_user.add("ROLE_USER");
        Client client1 = clientRepository.save(
                Client.builder()
                .clientId("inu12347890")
                .clientPassword("password1234@")
                .clientRole(role_user)
                .clientName("깨구리")
                .clientEmail("inu1234@naver.com")
                .clientPhoneNum("010-1234-5678")
                .build());

        Client client2 = clientRepository.save(
                Client.builder()
                .clientId("orange1234")
                .clientPassword("orange1234@")
                .clientRole(role_user)
                .clientName("오렌지")
                .clientEmail("inu123333@naver.com")
                .clientPhoneNum("010-1254-5679")
                .build()
        );

        Client findClient1 = clientRepository.findByClientId(client1.getClientId())
                .orElseThrow(() -> new NotFoundElementException("테스트DB에서 저장한 client가 찾아지지 않음.(1)"));
        Client findClient2 = clientRepository.findByClientId(client2.getClientId())
                .orElseThrow(() -> new NotFoundElementException("테스트DB에서 저장한 client가 찾아지지 않음.(2)"));

        Assertions.assertThat(clientRepository.count()).isEqualTo(2);
        Assertions.assertThat(findClient1.getClientId()).isEqualTo("inu12347890");
        Assertions.assertThat(findClient2.getClientId()).isEqualTo("orange1234");
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void deleteClient(){
        List<String> role_user = new ArrayList<>();
        role_user.add("ROLE_USER");
        Client client =clientRepository.save(
                Client.builder()
                        .clientId("hamster")
                        .clientPassword("pass1234@")
                        .clientRole(role_user)
                        .clientName("햄터")
                        .clientEmail("sssssss@naver.com")
                        .clientPhoneNum("010-1234-5678")
                        .build());
        clientRepository.delete(client);
        Assertions.assertThat(clientRepository.count()).isEqualTo(0);

    }
}
