package com.example.TodoProject.service;


import com.example.TodoProject.config.ex.DuplicatedException;
import com.example.TodoProject.config.ex.NotFoundException;
import com.example.TodoProject.controller.ClientController;
import com.example.TodoProject.dto.*;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<ResponseClientDto> getAllClient() {
        List<Client> allClients = clientRepository.findAll();
        List<ResponseClientDto> userInfoDtos = allClients.stream()
                .map(client -> new ResponseClientDto(
                        client.getClientNum(),
                        client.getClientId(),
                        client.getClientPassword(),
                        client.getClientName(),
                        client.getClientEmail(),
                        client.getClientRole(),
                        client.getClientPhoneNum()
                ))
                .collect(Collectors.toList());

        return userInfoDtos;
    }

    public void signUp(RequestClientDto requestClientDto){
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        Optional<Client> client = clientRepository.findByClientId(requestClientDto.getClientId());
        if(client.isPresent()){
            throw new DuplicatedException("중복된 아이디입니다.");
        }
        clientRepository.save(requestClientDto.toEntity(requestClientDto));
    }

    public void editClient(Long clientId, ShortClientDto shortClientDto){
        Client client = clientRepository.findByClientNum(clientId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        client.editUser(shortClientDto);
        clientRepository.save(client);
    }

}
