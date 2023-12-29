package com.study.toDoList.controller;


import com.study.toDoList.domain.Member;
import com.study.toDoList.dto.MemberResponseDto;
import com.study.toDoList.dto.MemberSaveDto;
import com.study.toDoList.dto.MemberUpdateDto;
import com.study.toDoList.dto.ResponseDto;
import com.study.toDoList.service.MemberService;
import com.study.toDoList.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final TaskService taskService;
    @Operation(summary = "회원 가입",description = "바디에 {email,password,nickname}을 json 형식으로 보내주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "회원가입성공")
    })
    @PostMapping("")
    public ResponseEntity<?> join(@Valid @RequestBody MemberSaveDto memberSaveDto){
        Long id = memberService.save(memberSaveDto);
        log.info("회원 join 호출 id:{}",id);
        return new ResponseEntity<>(new ResponseDto(id,"회원가입성공"), HttpStatus.CREATED);
    }

    @Operation(summary = "회원 정보 수정",description = "url 경로변수에 회원아이디,바디에 {password,nickname}을 json 형식으로 보내주세요.")
    @Parameter(name = "X-AUTH-TOKEN",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "회원정보수정성공")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MemberUpdateDto memberUpdateDto){
        log.info("회원 update 호출 회원id:{}",id);
        memberService.update(id,memberUpdateDto);
        return new ResponseEntity<>(new ResponseDto(id,"회원정보수정성공"), HttpStatus.OK);
    }
    @Operation(summary = "회원 삭제",description = "url 경로변수에 회원아이디를 담아 보내주세요")
    @Parameter(name = "X-AUTH-TOKEN",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "회원삭제성공")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("회원 delete 호출 회원id:{}",id);
        memberService.delete(id);
        return new ResponseEntity<>(new ResponseDto(id,"회원삭제성공"), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "회원 가져오기",description = "url 경로변수에 회원아이디를 담아 보내주세요")
    @Parameter(name = "X-AUTH-TOKEN",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "회원가져오기성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMember(@PathVariable Long id){
        log.info("회원 정보 호출 회원id:{}",id);
        return new ResponseEntity<>(memberService.getMember(id),HttpStatus.OK);
    }

    @Operation(summary = "회원의 모든 할일 가져오기",description = "url 경로변수에 회원아이디를 담아 보내주세요")
    @Parameter(name = "X-AUTH-TOKEN",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "회원의모든할일가져오기성공")
    })
    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getAllTask(@PathVariable Long id){
        log.info("회원의 모든 할일 호출 회원id:{}",id);
        return new ResponseEntity<>(taskService.getAllTask(id),HttpStatus.OK);
    }

    @Parameter(name = "X-AUTH-TOKEN",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @Operation(summary = "모든 회원 정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "모든회원정보가져오기성공")
    })
    @GetMapping("")
    public ResponseEntity<?> getAllMember(){
        log.info("모든 회원 정보 호출");
        return new ResponseEntity<>(memberService.getAllMember(),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestParam String id, @RequestParam String password){
        log.info("로그인 호출");
        String token = memberService.login(id,password);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }
}
