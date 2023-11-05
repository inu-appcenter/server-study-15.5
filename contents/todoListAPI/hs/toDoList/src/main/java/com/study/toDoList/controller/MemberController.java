package com.study.toDoList.controller;


import com.study.toDoList.domain.Member;
import com.study.toDoList.dto.MemberResponseDto;
import com.study.toDoList.dto.MemberSaveDto;
import com.study.toDoList.dto.MemberUpdateDto;
import com.study.toDoList.dto.ResponseDto;
import com.study.toDoList.service.MemberService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @Operation(summary = "회원 가입",description = "바디에 {email,password,nickname}을 json 형식으로 보내주세요.")
    @ApiResponses({
            @ApiResponse(code = 201,message = "회원가입성공")
    })
    @PostMapping("/api/member")
    public ResponseEntity<?> join(@RequestBody MemberSaveDto memberSaveDto){
        Long id = memberService.save(memberSaveDto);

        return new ResponseEntity<>(new ResponseDto(id,"회원가입성공"), HttpStatus.CREATED);
    }

    @Operation(summary = "회원 정보 수정",description = "url 경로변수에 회원아이디,바디에 {password,nickname}을 json 형식으로 보내주세요.")
    @ApiResponses({
            @ApiResponse(code = 200,message = "회원정보수정성공")
    })
    @PutMapping("/api/member/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MemberUpdateDto memberUpdateDto){
        memberService.update(id,memberUpdateDto);
        return new ResponseEntity<>(new ResponseDto(id,"회원정보수정성공"), HttpStatus.OK);
    }
    @Operation(summary = "회원 삭제",description = "url 경로변수에 회원아이디를 담아 보내주세요")
    @ApiResponses({
            @ApiResponse(code = 204,message = "회원삭제성공")
    })
    @DeleteMapping("/api/member/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        memberService.delete(id);
        return new ResponseEntity<>(new ResponseDto(id,"회원삭제성공"), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "회원 가져오기",description = "url 경로변수에 회원아이디를 담아 보내주세요")
    @ApiResponses({
            @ApiResponse(code = 200,message = "회원가져오기성공")
    })
    @GetMapping("/api/member/{id}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long id){
        return ResponseEntity.ok().body(memberService.getMember(id));
    }
}
