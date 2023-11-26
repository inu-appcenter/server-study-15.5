package com.example.todolist.Controller;

import com.example.todolist.DTO.CommonResponseDTO;
import com.example.todolist.DTO.Reply.AddReplyReqDTO;
import com.example.todolist.DTO.Reply.ChangeReplyReqDTO;
import com.example.todolist.Service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Reply")
public class ReplyController {

    private final ReplyService replyService;
    private final Long userId = 4l;
    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService=replyService;
    }

    @PostMapping("/{toDoId}/replys")
    @Operation(summary = "댓글 추가", description = "1. userId값은 jwt토큰에서 추출합니다. 아직 구현이 되지 않아 임의의 값(4)으로 처리중이며 userId값은 입력하지 않아도 됩니다.<br>2.헤더에 인증토큰을 보내야 하며 로그인 성공 시 발급됩니다.<br>3. todoId값은 정수값이여야 하며 uri에서 값을 추출합니다. 요청바디에는 해당 값을 보내지 않아도 됩니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "댓글 추가성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> addReply(@PathVariable Long toDoId, @RequestBody @Valid AddReplyReqDTO addReplyReqDTO){
        /*
            토큰에서 userId값 추출 로직
        */
        addReplyReqDTO.setUserId(userId);
        addReplyReqDTO.setToDoId(toDoId);

        replyService.addReply(addReplyReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDTO.of("CREATED","댓글 추가성공",null));
    }

    @PatchMapping("/replys/{replyId}")
    @Operation(summary = "댓글 수정",description = "1. userId값은 jwt토큰에서 추출합니다. 아직 구현이 되지 않아 임의의 값(4)으로 처리중이며 userId값은 입력하지 않아도 됩니다.<br>2.헤더에 인증토큰을 보내야 하며 로그인 성공 시 발급됩니다.<br>3. replyId값은 정수값이여야 하며 uri에서 값을 추출합니다. 요청바디에는 해당 값을 보내지 않아도 됩니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "댓글 수정성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> changeReply(@PathVariable Long replyId,@RequestBody @Valid ChangeReplyReqDTO changeReplyReqDTO){
        /*
            토큰에서 userId값 추출 로직
        */
        changeReplyReqDTO.setReplyId(replyId);
        changeReplyReqDTO.setUserId(userId);

        replyService.changeReply(changeReplyReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","댓글 변경성공",null));
    }

    @DeleteMapping("/replys/{replyId}")
    @Operation(summary = "댓글 삭제", description = "1. userId값은 jwt토큰에서 추출합니다. 아직 구현이 되지 않아 임의의 값(4)으로 처리중이며 userId값은 입력하지 않아도 됩니다.<br>2.헤더에 인증토큰을 보내야 하며 로그인 성공 시 발급됩니다.<br>3. replyId값은 정수값이여야 하며 uri에서 값을 추출합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "댓글 삭제성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> deleteReply(@PathVariable Long replyId){
        /*
            토큰에서 userId값 추출 로직
        */

        replyService.deleteReply(userId,replyId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","댓글 삭제성공",null));
    }
}