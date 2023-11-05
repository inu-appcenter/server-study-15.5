package com.example.TodoProject.controller;


import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.CommonResponseDto;
import com.example.TodoProject.dto.RequestClientDto;
import com.example.TodoProject.dto.RequestTodoDto;
import com.example.TodoProject.dto.ShortClientDto;
import com.example.TodoProject.entity.Client;
import com.example.TodoProject.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    /*
    기능을 추가하자
        관리자용
            1. 유저 전체 조회
        사용자용
            1. 회원가입
            2. 로그인 -->추후에 구현
            3. 회원정보수정
            4. 회원탈퇴 -->추후에 구현
     */

    private final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //유저 전체 조회
    @Operation(summary = "유저 전체 조회", description = "관리자가 유저를 관리할 때 쓸 수 있는 도구. 시큐리티 추가 후 권한 설정 예정")
    @GetMapping("/allClients")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "잘못된 요청입니다."),
    })
    public ResponseEntity<CommonResponseDto> getAllClient(){
        List<RequestClientDto> clients = clientService.getAllClient();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"조회성공" ,clients));
    }

    //유저 생성하기
    @Operation(summary = "유저 생성(회원가입)", description = "RequestClientDto를 파라미터로 받음. 유저의 회원가입을 담당함.")
    @PostMapping("/sign-up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    public ResponseEntity<CommonResponseDto> signUp(@RequestBody RequestClientDto requestClientDto){
        LOGGER.info("[signUp] 회원가입중... id : {}", requestClientDto.getClientId());
        clientService.signUp(requestClientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto(CommonResponse.SUCCESS, "회원가입 성공", requestClientDto.getClientId()));
    }

    //유저 수정하기
    @Operation(summary = "유저 회원정보 수정", description = "clientNum과 ShortClientDto를 파라미터로 받음. 유저의 회원정보를 수정한다.(아이디 수정 불가능)")
    @PostMapping("/edit")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원정보 수정 성공"),
    })
    public ResponseEntity<CommonResponseDto> editUser(Long clientNum, @RequestBody ShortClientDto shortClientDto){
        LOGGER.info("[editUser] 회원정보수정");
        clientService.editClient(clientNum, shortClientDto);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto(CommonResponse.SUCCESS, "회원정보 수정 성공", "null"));
    }
}
