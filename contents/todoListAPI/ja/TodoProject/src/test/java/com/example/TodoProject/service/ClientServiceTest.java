package com.example.TodoProject.service;

import com.example.TodoProject.dto.Client.ClientRequestDto;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    void getAllClient(){

    }

    @Test
    void signUp(){
        ClientRequestDto.RequestClientDto requestClientDto = ClientRequestDto.RequestClientDto.builder()
                .clientId("matchingA")
                .clientPassword("password134@")
                .clientName("개굴이")
                .clientEmail("inu1324@naver.com")
                .clientPhoneNum("010-1236-9839")
                .clientRole("ROLE_USER")
                .build();
        given(clientRepository.findByClientId(requestClientDto.getClientId())).willReturn(java.util.Optional.empty());

        // When
        clientService.signUp(requestClientDto);

        // Then
        verify(clientRepository).findByClientId(requestClientDto.getClientId());
        verify(clientRepository).save(any());

    }

    @Test
    void deleteClient(){
        ClientRequestDto.RequestClientDto requestClientDto = ClientRequestDto.RequestClientDto.builder()
                .clientId("matchingA")
                .clientPassword("password134@")
                .clientName("개굴이")
                .clientEmail("inu1324@naver.com")
                .clientPhoneNum("010-1236-9839")
                .clientRole("ROLE_USER")
                .build();

        Client client = requestClientDto.toEntity(requestClientDto);

        given(clientRepository.findByClientNum(client.getClientNum())).willReturn(java.util.Optional.of(client));

        // When
        clientService.deleteClient(client.getClientNum());

        // Then
        verify(clientRepository).findByClientNum(client.getClientNum());
        verify(clientRepository).delete(client);

    }

}
