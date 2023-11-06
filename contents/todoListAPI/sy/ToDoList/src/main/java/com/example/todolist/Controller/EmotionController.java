package com.example.todolist.Controller;

import com.example.todolist.Service.EmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmotionController {

    private final EmotionService emotionService;

    @Autowired
    public EmotionController(EmotionService emotionService){
        this.emotionService=emotionService;
    }

    @PostMapping("/emotions/{toDoId}")
    public ResponseEntity<Object> addEmotion(@PathVariable Long toDoId){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 1l; // 임시로 userId값 설정

        emotionService.addEmotion(userId,toDoId);
        return ResponseEntity.status(201).body(null);
    }

    @DeleteMapping("/emotions/{toDoId}")
    public ResponseEntity<Object> deleteEmotion(@PathVariable Long toDoId){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 1l; // 임시로 userId값 설정

        emotionService.deleteEmotion(userId,toDoId);
        return ResponseEntity.status(200).body(null);
    }
}
