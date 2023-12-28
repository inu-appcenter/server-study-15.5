package com.example.TodoProject.service;


import com.example.TodoProject.config.ex.DuplicatedException;
import com.example.TodoProject.config.ex.LoginErrorException;
import com.example.TodoProject.config.ex.NotFoundElementException;
import com.example.TodoProject.config.security.JwtProvider;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public JwtProvider jwtProvider;

    public PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder){
        this.clientRepository = clientRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
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
                .ifPresent(client -> {throw new DuplicatedException("중복된 아이디입니다.");});

        clientRepository.save(requestClientDto.toEntity(requestClientDto,  passwordEncoder.encode(requestClientDto.getClientPassword())));
        log.info("[signUp] 회원 가입 성공. client Num: {}", requestClientDto.getClientId());
    }


    public void editClient(String token, EditClientDto editClientDto){
        log.info("[editClient] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[editClient] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));
        client.editUser(editClientDto);
        clientRepository.save(client);
    }

    public void deleteClient(String token){
        log.info("[deleteClient] 토큰에서 유저 정보 추출");
        Long clientNum = jwtProvider.getClientNum(token);
        log.info("[deleteClient] 토큰에서 유저 정보 추출 성공. clientnum = {}", clientNum);
        Client client = clientRepository.findByClientNum(clientNum)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 유저입니다."));
        clientRepository.delete(client);
    }

    public String signIn(RequestLoginDto requestLoginDto){
        Client client = clientRepository.findByClientId(requestLoginDto.getClientId())
                .orElseThrow(()-> new LoginErrorException("아이디 혹은 비밀번호가 틀렸습니다."));

        if(!passwordEncoder.matches(requestLoginDto.getClientPassword(), client.getPassword())){
            throw new LoginErrorException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
        else{
            String token = jwtProvider.createToken(client.getClientId(),client.getClientNum(), client.getClientRole());
            log.info("토큰 생성 완료. 토큰: {}", token);
            return token;
        }
    }

    public void duplicationCheck(String clientId){
        log.info("[duplicationCheck] 아이디 중복 확인");
        clientRepository.findByClientId(clientId)
                .ifPresent(client -> {throw new DuplicatedException("중복된 아이디입니다.");});
        log.info("[duplicationCheck] 사용 가능한 아이디입니다.");

    }

}
