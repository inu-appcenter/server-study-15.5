package com.example.todolist.Controller;

import com.example.todolist.DTO.CommonResponseDTO;
import com.example.todolist.Security.JwtProvider;
import com.example.todolist.Service.EmotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "Emotion")
public class EmotionController {

    private final EmotionService emotionService;
    private final JwtProvider jwtProvider;
    @Autowired
    public EmotionController(EmotionService emotionService,JwtProvider jwtProvider){
        this.emotionService=emotionService;
        this.jwtProvider=jwtProvider;
    }

    @PostMapping("/emotions/{toDoId}")
    @Operation(summary = "이모션  추가" , description = "1. userId값은 jwt토큰에서 추출합니다. 아직 구현이 되지 않아 임의의 값(4)으로 처리중이며 userId값은 입력하지 않아도 됩니다.<br>2.헤더에 인증토큰을 보내야 하며 로그인 성공 시 발급됩니다.<br>3. todoId값은 정수값이여야 하며 uri에서 값을 추출합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Emotion 추가성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> addEmotion(@PathVariable Long toDoId, HttpServletRequest request){

        emotionService.addEmotion(jwtProvider.readUserIdByToken(request),toDoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDTO.of("CREATED","이모션 추가성공",null));
    }
    @DeleteMapping("/emotions/{toDoId}")
    @Operation(summary = "이모션 삭제", description = "1. userId값은 jwt토큰에서 추출합니다. 아직 구현이 되지 않아 임의의 값(4)으로 처리중이며 userId값은 입력하지 않아도 됩니다.<br>2.헤더에 인증토큰을 보내야 하며 로그인 성공 시 발급됩니다.<br>3. todoId값은 정수값이여야 하며 uri에서 값을 추출합니다. 요청바디에는 해당 값을 보내지 않아도 됩니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Emotion 삭제성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> deleteEmotion(@PathVariable Long toDoId, HttpServletRequest request){

        emotionService.deleteEmotion(jwtProvider.readUserIdByToken(request),toDoId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","이모션 삭제성공",null));
    }
}