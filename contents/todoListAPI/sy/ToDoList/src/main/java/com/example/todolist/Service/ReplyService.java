package com.example.todolist.Service;

import com.example.todolist.DTO.Reply.AddReplyReqDTO;
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

        Reply reply = Reply.builder()
                .content(addReplyReqDTO.getContent())
                .user(user)
                .toDo(toDo)
                .build();

        replyRepository.save(reply);
    }
    public List<ReadReplyResDTO> readReply(List<Reply> replyList){

        List<ReadReplyResDTO> readReplyResDTOList = new ArrayList<>();

        for(int i=0;i<replyList.size();i++){

            Reply reply = replyList.get(i);

            ReadReplyResDTO readReplyResDTO = ReadReplyResDTO.builder()
                    .replyId(reply.getReplyId())
                    .content(reply.getContent())
                    .writer(reply.getUser().getName())
                    .build();

            readReplyResDTOList.add(readReplyResDTO);
        }
        return readReplyResDTOList;
    }
    public void changeReply(){

    }
    public void deleteReply(){

    }
}
