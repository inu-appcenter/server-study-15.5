package com.example.todolist.Service;

import com.example.todolist.DTO.ToDo.AddToDoReqDTO;
import com.example.todolist.DTO.ToDo.ReadToDoPreviewResDTO;
import com.example.todolist.DTO.ToDo.ReadToDoResDTO;
import com.example.todolist.DTO.ToDo.UpdateToDoReqDTO;
import com.example.todolist.Exception.CommondException;
import com.example.todolist.Exception.ExceptionCode;
import com.example.todolist.Repository.ToDoRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.domain.ToDo;
import com.example.todolist.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;
    private final ReplyService replyService;
    private final EmotionService emotionService;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository,ReplyService replyService,
                       EmotionService emotionService){
        this.toDoRepository=toDoRepository;
        this.userRepository=userRepository;
        this.replyService=replyService;
        this.emotionService=emotionService;
    }

    public void addToDo(AddToDoReqDTO addToDoReqDTO){ // Todo추가 로직

        User user = userRepository.findById(addToDoReqDTO.getUserId())
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));

        ToDo toDo = addToDoReqDTO.toEntity(addToDoReqDTO);
        toDo.addUser(user);

        toDoRepository.save(toDo);
    }
    @Transactional
    public void updateToDo(UpdateToDoReqDTO updateToDoReqDTO){ // ToDo수정 로직

        ToDo toDo = toDoRepository.findById(updateToDoReqDTO.getToDoId())
                .orElseThrow(() -> new CommondException(ExceptionCode.TODO_NOTFOUND));
        User user = toDo.getUser();
        if(!isMyToDo(updateToDoReqDTO.getUserId(),user)){ // 자신이 쓴 ToDo가 아니라면
            throw new CommondException(ExceptionCode.NOT_MYTODO);
        }

        updateToDoReqDTO.changeToDo(toDo,updateToDoReqDTO);
        toDoRepository.save(toDo);
    }

    @Transactional(readOnly = true)
    public ReadToDoResDTO readToDo(Long toDoId,Long userId){  // 단일 ToDo조회 로직

        ToDo toDo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new CommondException(ExceptionCode.TODO_NOTFOUND));
        User user = toDo.getUser();

        // dto에 정적팩토리 메소드를 생성하려 했는데 reply와 emotion서비스 로직이 필요해서 ToDoSerivce에 메소드로 뺐습니다.
        return toReadToDoResDTO(toDo,user,userId,toDoId);
    }

    @Transactional(readOnly = true)
    public List<ReadToDoPreviewResDTO> readToDoPreviewList(){

        List<ToDo> toDoList = toDoRepository.findAll();

        // dto에 정적팩토리 메소드를 생성하려 했는데 reply와 emotion서비스 로직이 필요해서 ToDoSerivce에 메소드로 뺐습니다.
        return toReadToDoPreviewResDTO(toDoList);
    }
    @Transactional
    public void deleteToDo(Long toDoId, Long userId){

        ToDo toDo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new CommondException(ExceptionCode.TODO_NOTFOUND));
        User user = toDo.getUser();
        if(!isMyToDo(userId,user)){ // 자신이 쓴 ToDo가 아니라면
            throw new CommondException(ExceptionCode.NOT_MYTODO);
        }

        toDoRepository.delete(toDo);
    }

    public boolean isMyToDo(Long userId, User user){ // 자신이 쓴 ToDo인지 아닌지를 반환하는 메소드

        if(!userId.equals(user.getUserId())){ // 자신이 쓴 ToDo가 아니라면
            return false;
        }

        return true;
    }

    public ReadToDoResDTO toReadToDoResDTO(ToDo toDo,User user,Long userId, Long toDoId){

        boolean isMyToDo = true;
        if(!isMyToDo(userId,user)){ // 자신이 쓴 ToDo가 아니라면
            isMyToDo=false;
        }

        return ReadToDoResDTO.builder()
                .title(toDo.getTitle())
                .content(toDo.getContent())
                .dueDate(toDo.getDueDate())
                .readReplyResDTOList(replyService.readReply(userId,toDo.getReply()))
                .isFinished(toDo.isFinished())
                .writerName(user.getName())
                .likeCnt(emotionService.findLikeCnt(toDoId))
                .regDate(toDo.getRegDate())
                .modDate(toDo.getModDate())
                .isMyToDo(isMyToDo)
                .build();
    }

    public List<ReadToDoPreviewResDTO> toReadToDoPreviewResDTO(List<ToDo> toDoList){

        List<ReadToDoPreviewResDTO> readToDoPreviewResDTOList = new ArrayList<>();

        for(int i=0;i<toDoList.size();i++){
            ToDo toDo = toDoList.get(i);
            ReadToDoPreviewResDTO readToDoPreviewResDTO = ReadToDoPreviewResDTO.builder()
                    .toDoId(toDo.getToDoId())
                    .title(toDo.getTitle())
                    .isFinished(toDo.isFinished())
                    .writerName(toDo.getUser().getName())
                    .likeCnt(emotionService.findLikeCnt(toDo.getToDoId()))
                    .build();

            readToDoPreviewResDTOList.add(readToDoPreviewResDTO);
        }
        return readToDoPreviewResDTOList;
    }
}
