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
    public ResponseEntity<Object> addReply(@PathVariable Long toDoId,@RequestBody AddReplyReqDTO addReplyReqDTO){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 2l; // 임시로 userId값 설정

        addReplyReqDTO.setUserId(userId);
        addReplyReqDTO.setToDoId(toDoId);

        replyService.addReply(addReplyReqDTO);
        return ResponseEntity.status(201).body(null);
    }

    @PatchMapping("/replys/{replyId}")
    public ResponseEntity<Object> changeReply(@PathVariable Long replyId,@RequestBody ChangeReplyReqDTO changeReplyReqDTO){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 2l; // 임시로 userId값 설정

        changeReplyReqDTO.setReplyId(replyId);
        changeReplyReqDTO.setUserId(userId);

        replyService.changeReply(changeReplyReqDTO);
        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/replys/{replyId}")
    public ResponseEntity<Object> deleteReply(@PathVariable Long replyId){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 2l; // 임시로 userId값 설정

        replyService.deleteReply(userId,replyId);
        return ResponseEntity.status(200).body(null);
    }
}
