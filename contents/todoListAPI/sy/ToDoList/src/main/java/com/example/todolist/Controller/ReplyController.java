package com.example.todolist.Controller;

import com.example.todolist.DTO.Reply.AddReplyReqDTO;
import com.example.todolist.DTO.Reply.ChangeReplyReqDTO;
import com.example.todolist.Service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService=replyService;
    }

    @PostMapping("/{toDoId}/replys")
    public ResponseEntity<Object> addReply(@PathVariable Long toDoId,String content){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 1l; // 임시로 userId값 설정

        AddReplyReqDTO addReplyReqDTO = AddReplyReqDTO.builder()
                .content(content)
                .toDoId(toDoId)
                .userId(userId)
                .build();

        replyService.addReply(addReplyReqDTO);
        return ResponseEntity.status(201).body(null);
    }

    @PatchMapping("/to-dos/{replyId}")
    public ResponseEntity<Object> changeReply(@PathVariable Long replyId,String content){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 1l; // 임시로 userId값 설정

        ChangeReplyReqDTO changeReplyReqDTO = ChangeReplyReqDTO.builder()
                .replyId(replyId)
                .userId(userId)
                .content(content)
                .build();

        replyService.changeReply(changeReplyReqDTO);
        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/to-dos/{replyId}")
    public ResponseEntity<Object> deleteReply(@PathVariable Long replyId){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 1l; // 임시로 userId값 설정

        replyService.deleteReply(userId,replyId);
        return ResponseEntity.status(200).body(null);
    }
}
