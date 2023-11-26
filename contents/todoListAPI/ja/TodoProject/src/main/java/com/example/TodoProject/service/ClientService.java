package com.example.TodoProject.service;


import com.example.TodoProject.config.ex.DuplicatedException;
import com.example.TodoProject.config.ex.NotFoundElementException;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
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
        clientRepository.findByClientId(requestClientDto.getClientId())
                .ifPresent(client -> new DuplicatedException("중복된 아이디입니다."));
        clientRepository.save(requestClientDto.toEntity(requestClientDto));
        log.info("[signUp] 회원 가입 성공. client Num: {}", requestClientDto.getClientId());
    }

    public void editClient(Long clientId, EditClientDto editClientDto){
        Client client = clientRepository.findByClientNum(clientId)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));
        client.editUser(editClientDto);
        clientRepository.save(client);
    }

    public void deleteClient(Long clientId){
        Client client = clientRepository.findByClientNum(clientId)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));
        clientRepository.delete(client);
    }

}
