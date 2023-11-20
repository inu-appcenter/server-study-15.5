package com.example.TodoProject.service;


import com.example.TodoProject.config.ex.DuplicatedException;
import com.example.TodoProject.config.ex.NotFoundException;
import com.example.TodoProject.controller.ClientController;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.TodoProject.dto.Client.ClientRequestDto.*;
import static com.example.TodoProject.dto.Client.ClientResponseDto.*;

@Service
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<ResponseClientDto> getAllClient() {
        log.info("[getAllClient] 유저 전체 출력");
        List<Client> allClients = clientRepository.findAll();
        List<ResponseClientDto> userInfoDtos = allClients.stream()
                .map(client -> client.toDto()
                )
                .collect(Collectors.toList());

        return userInfoDtos;
    }

    public void signUp(RequestClientDto requestClientDto){
        log.info("[signUp] 회원 가입 정보 전달");
        Optional<Client> client = clientRepository.findByClientId(requestClientDto.getClientId());
        if(client.isPresent()){
            throw new DuplicatedException("중복된 아이디입니다.");
        }
        clientRepository.save(requestClientDto.toEntity(requestClientDto));
        log.info("[signUp] 회원 가입 성공. client Num: {}", requestClientDto.getClientId());
    }

    public void editClient(Long clientId, EditClientDto editClientDto){
        Client client = clientRepository.findByClientNum(clientId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        client.editUser(editClientDto);
        clientRepository.save(client);
    }

}
