package com.example.TodoProject.service;

import com.example.TodoProject.dto.Client.ClientRequestDto;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void getAllClient(){

    }

    @Test
    void signUp(){
        List<String> role_user = new ArrayList<>();
        role_user.add("ROLE_USER");

        ClientRequestDto.RequestClientDto requestClientDto = ClientRequestDto.RequestClientDto.builder()
                .clientId("matchingA")
                .clientPassword("password134@")
                .clientName("개굴이")
                .clientEmail("inu1324@naver.com")
                .clientPhoneNum("010-1236-9839")
                .clientRole(role_user)
                .build();


        given(clientRepository.findByClientId(requestClientDto.getClientId())).willReturn(java.util.Optional.empty());
        // save 메서드가 호출될 때 반환 값 설정
        when(clientRepository.save(any())).thenAnswer(invocation -> {
            Client savedClient = invocation.getArgument(0);
            return savedClient;
        });

        // passwordEncoder.encode 메서드의 반환 값 설정
        when(passwordEncoder.encode("password134@")).thenReturn("ewjoipcpfmjwiepcjqiwpo");

        // 테스트 대상 메서드 호출
        clientService.signUp(requestClientDto);

        // Then
        verify(clientRepository).findByClientId(requestClientDto.getClientId());
        verify(clientRepository).save(any());
        verify(passwordEncoder).encode("password134@");

    }

    @Test
    void deleteClient(){
//        List<String> role_user = new ArrayList<>();
//        role_user.add("ROLE_USER");
//        ClientRequestDto.RequestClientDto requestClientDto = ClientRequestDto.RequestClientDto.builder()
//                .clientId("matchingA")
//                .clientPassword(" ")
//                .clientName("개굴이")
//                .clientEmail("inu1324@naver.com")
//                .clientPhoneNum("010-1236-9839")
//                .clientRole(role_user)
//                .build();
//
//        Client client = requestClientDto.toEntity(requestClientDto, passwordEncoder.encode(requestClientDto.getClientPassword()));
//
//        given(clientRepository.findByClientNum(client.getClientNum())).willReturn(java.util.Optional.of(client));
//
//        // When
//        clientService.deleteClient(client.getClientNum());
//
//        // Then
//        verify(clientRepository).findByClientNum(client.getClientNum());
//        verify(clientRepository).delete(client);

    }

}
