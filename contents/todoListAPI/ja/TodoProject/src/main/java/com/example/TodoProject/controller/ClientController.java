package com.example.TodoProject.controller;


import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.CommonResponseDto;
import com.example.TodoProject.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.TodoProject.dto.Client.ClientRequestDto.*;
import static com.example.TodoProject.dto.Client.ClientResponseDto.*;

@Slf4j
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
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //유저 전체 조회
    @Operation(summary = "유저 전체 조회", description = "관리자가 유저를 관리할 때 쓸 수 있는 도구. 시큐리티 추가 후 권한 설정 예정<br><br>입력: 없음<br> 출력: ResponseClientDto를 List 형식으로 data에 반환.")
    @GetMapping("")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 전체 조회", content = @Content(schema = @Schema(implementation = ResponseClientListDto.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없다 이 바보야!")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "조회 성공", response = ResponseClientListDto.class)
    )
    public ResponseEntity<CommonResponseDto> getAllClient(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS,"조회성공" ,clientService.getAllClient()));
    }

    //유저 생성하기
    @Operation(summary = "유저 생성(회원가입)", description = "유저의 회원가입을 담당함.<br><br> 입력: RequestClientDto <br> 출력: data에 null을 반환.")
    @PostMapping("/sign-up")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "아이디가 중복되었습니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")
    })
//    @io.swagger.annotations.ApiResponses(
//            @io.swagger.annotations.ApiResponse(code = 201, message = "회원가입 성공", response = ResponseClientSignDto.class)
//    )
    public ResponseEntity<CommonResponseDto> signUp(@RequestBody @Valid RequestClientDto requestClientDto) {
        log.info("[signUp] 회원가입중... id : {}", requestClientDto.getClientId());

        // 회원가입 로직 실행
        clientService.signUp(requestClientDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "회원가입 성공", null));
    }

    //유저 수정하기
    @Operation(summary = "유저 회원정보 수정", description = "유저의 회원정보를 수정한다.<br><br> 특이사항: 아이디 수정 불가능합니다. 헷갈리지 마십쇼! <br><br> 입력: EditClientDto <br> 출력: data에 null 반환")
    @PutMapping("")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 회원정보 수정 성공"),
            @ApiResponse(responseCode   = "404", description = "유저를 찾을 수 없습니다")
    })
    public ResponseEntity<CommonResponseDto> editClient(@RequestHeader("X-AUTH-TOKEN") String token ,@Valid @RequestBody EditClientDto editClientDto){
        log.info("[editUser] 회원정보수정");
        clientService.editClient(token, editClientDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "회원정보 수정 성공", null));
    }

    @Operation(summary="유저 회원탈퇴", description = "유저의 번호를 파라미터로 입력하면 유저를 영구 삭제함. <br><br> 특이사항: 유저가 가지고 있는 투두까지 삭제됨 <br><br> 입력: 삭제하고싶은 클라이언트의 데이터베이스 상 PK <br> 출력: data에 null로 반환함.")
    @DeleteMapping("")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수가 없네요;;")
    })
    public ResponseEntity<CommonResponseDto> deleteClient(@RequestHeader("X-AUTH-TOKEN") String token){
        log.info("[deleteClient] 회원탈퇴");
        clientService.deleteClient(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "회원탈퇴 성공", null));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<CommonResponseDto> signIn(@RequestBody RequestLoginDto requestLoginDto){
        log.info("[signIn] 로그인 시도");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "로그인 성공", clientService.signIn(requestLoginDto)));
    }

    @PostMapping("/duplicationcheck")
    public ResponseEntity<CommonResponseDto> duplicationCheck(String clientId){
        log.info("[duplicationCheck] 아이디 중복 확인");
        clientService.duplicationCheck(clientId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(CommonResponse.SUCCESS, "중복되는 아이디가 없습니다", null));
    }

}
