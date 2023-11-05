package com.example.TodoProject.service;


import com.example.TodoProject.controller.ClientController;
import com.example.TodoProject.dto.RequestClientDto;
import com.example.TodoProject.dto.RequestTodoGroupDto;
import com.example.TodoProject.dto.ShortClientDto;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<RequestClientDto> getAllClient() {
        List<Client> allClients = clientRepository.findAll();
        List<RequestClientDto> userInfoDtos =  new ArrayList<>();
        for(Client client : allClients ){
            RequestClientDto clientInfoDto =  new RequestClientDto(client.getClientId(), client.getClientPassword(), client.getClientName(), client.getClientEmail(), client.getClientRole(), client.getClientPhoneNum());
            userInfoDtos.add(clientInfoDto);
        }
        return userInfoDtos;
    }

    public void signUp(RequestClientDto requestClientDto){
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        Client client;
        client = requestClientDto.toEntity(requestClientDto);
        clientRepository.save(client);
    }

    public void editClient(Long clientId, ShortClientDto shortClientDto){
        Client client = clientRepository.findByClientNum(clientId)
                .orElseThrow(() -> new RuntimeException());
        client.editUser(shortClientDto);
        clientRepository.save(client);
    }

}
