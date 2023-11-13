package com.example.todolist.Controller;

import com.example.todolist.DTO.Reply.AddReplyReqDTO;
import com.example.todolist.DTO.Reply.ChangeReplyReqDTO;
import com.example.todolist.Service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService=replyService;
    }

    @PostMapping("/{toDoId}/replys")
    @ApiOperation(value = "댓글 추가")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "댓글 추가성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<Void> addReply(@PathVariable Long toDoId,@RequestBody AddReplyReqDTO addReplyReqDTO){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 3l; // 임시로 userId값 설정

        addReplyReqDTO.setUserId(userId);
        addReplyReqDTO.setToDoId(toDoId);

        replyService.addReply(addReplyReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PatchMapping("/replys/{replyId}")
    @ApiOperation(value = "댓글 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "댓글 수정성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<Void> changeReply(@PathVariable Long replyId,@RequestBody ChangeReplyReqDTO changeReplyReqDTO){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 3l; // 임시로 userId값 설정

        changeReplyReqDTO.setReplyId(replyId);
        changeReplyReqDTO.setUserId(userId);

        replyService.changeReply(changeReplyReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/replys/{replyId}")
    @ApiOperation(value = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "댓글 삭제성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<Void> deleteReply(@PathVariable Long replyId){
        /*
            토큰에서 userId값 추출 로직
        */
        Long userId = 3l; // 임시로 userId값 설정

        replyService.deleteReply(userId,replyId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
