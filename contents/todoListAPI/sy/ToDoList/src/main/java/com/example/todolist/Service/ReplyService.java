package com.example.todolist.Service;

import com.example.todolist.DTO.Reply.AddReplyReqDTO;
import com.example.todolist.DTO.Reply.ChangeReplyReqDTO;
import com.example.todolist.DTO.Reply.ReadReplyResDTO;
import com.example.todolist.Repository.ReplyRepository;
import com.example.todolist.Repository.ToDoRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.domain.Reply;
import com.example.todolist.domain.ToDo;
import com.example.todolist.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        User user = userRepository.findById(addReplyReqDTO.getUserId()).orElseThrow(/*예외처리*/);
        ToDo toDo= toDoRepository.findById(addReplyReqDTO.getToDoId()).orElseThrow(/*예외처리*/);

        replyRepository.save(AddReplyReqDTO.toEntity(addReplyReqDTO,user,toDo));
    }
    public List<ReadReplyResDTO> readReply(Long userId,List<Reply> replyList){

        List<ReadReplyResDTO> readReplyResDTOList = new ArrayList<>();

        for(int i=0;i<replyList.size();i++){

            Reply reply = replyList.get(i);
            readReplyResDTOList.add(ReadReplyResDTO.toDto(reply,isMyReply(userId,reply)));
        }
        return readReplyResDTOList;
    }
    public void changeReply(ChangeReplyReqDTO changeReplyReqDTO){
        Reply reply = replyRepository.findById(changeReplyReqDTO.getReplyId()).orElseThrow(/*예외처리*/);
        if(!isMyReply(changeReplyReqDTO.getUserId(), reply)){
            //예외처리
        }

        reply.changeContent(changeReplyReqDTO.getContent());
        replyRepository.save(reply);
    }
    public void deleteReply(Long userId, Long replyId){
        Reply reply = replyRepository.findById(replyId).orElseThrow(/*예외처리*/);

        if(!isMyReply(userId,reply)){
            //예외처리
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
