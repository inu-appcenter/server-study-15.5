package com.example.todolist.Service;

import com.example.todolist.DTO.Reply.AddReplyReqDTO;
import com.example.todolist.DTO.Reply.ChangeReplyReqDTO;
import com.example.todolist.DTO.Reply.ReadReplyResDTO;
import com.example.todolist.Exception.CommondException;
import com.example.todolist.Exception.ExceptionCode;
import com.example.todolist.Repository.ReplyRepository;
import com.example.todolist.Repository.ToDoRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.domain.Reply;
import com.example.todolist.domain.ToDo;
import com.example.todolist.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository,UserRepository userRepository, ToDoRepository toDoRepository){
        this.replyRepository=replyRepository;
        this.userRepository=userRepository;
        this.toDoRepository=toDoRepository;
    }

    public void addReply(AddReplyReqDTO addReplyReqDTO){

        User user = userRepository.findById(addReplyReqDTO.getUserId())
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));
        ToDo toDo= toDoRepository.findById(addReplyReqDTO.getToDoId())
                .orElseThrow(() -> new CommondException(ExceptionCode.TODO_NOTFOUND));

        replyRepository.save(AddReplyReqDTO.toEntity(addReplyReqDTO,user,toDo));
    }
    public List<ReadReplyResDTO> readReply(Long userId,List<Reply> replyList){

        List<ReadReplyResDTO> readReplyResDTOList =
                replyList.stream().map(e -> ReadReplyResDTO.toDto(e,isMyReply(userId,e))).collect(Collectors.toList());

        return readReplyResDTOList;
    }
    public void changeReply(ChangeReplyReqDTO changeReplyReqDTO){
        Reply reply = replyRepository.findById(changeReplyReqDTO.getReplyId())
                .orElseThrow(() -> new CommondException(ExceptionCode.REPLY_NOTFOUND));
        if(!isMyReply(changeReplyReqDTO.getUserId(), reply)){
            throw new CommondException(ExceptionCode.NOT_MYREPLY);
        }

        reply.changeContent(changeReplyReqDTO.getContent());
        replyRepository.save(reply);
    }
    public void deleteReply(Long userId, Long replyId){
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CommondException(ExceptionCode.REPLY_NOTFOUND));

        if(!isMyReply(userId,reply)){
            throw new CommondException(ExceptionCode.NOT_MYREPLY);
        }

        replyRepository.delete(reply);
    }

    public boolean isMyReply(Long userId, Reply reply){
        if(userId.equals(reply.getUser().getUserId())){
            return true;
        }
        return false;
    }
}
