package com.example.todolist.Controller;

import com.example.todolist.Service.EmotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Emotion")
public class EmotionController {

    private final EmotionService emotionService;
    private final Long userId = 3l;
    @Autowired
    public EmotionController(EmotionService emotionService){
        this.emotionService=emotionService;
    }

    @PostMapping("/emotions/{toDoId}")
    @ApiOperation(value = "이모션 추가")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Emotion 추가성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<Void> addEmotion(@PathVariable Long toDoId){
        /*
            토큰에서 userId값 추출 로직
        */
        emotionService.addEmotion(userId,toDoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/emotions/{toDoId}")
    @ApiOperation(value = "이모션 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Emotion 삭제성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<Void> deleteEmotion(@PathVariable Long toDoId){
        /*
            토큰에서 userId값 추출 로직
        */
        emotionService.deleteEmotion(userId,toDoId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
