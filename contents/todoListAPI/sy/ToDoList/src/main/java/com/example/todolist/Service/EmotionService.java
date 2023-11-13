package com.example.todolist.Service;

import com.example.todolist.Exception.CommondException;
import com.example.todolist.Exception.ExceptionCode;
import com.example.todolist.Repository.EmotionRepository;
import com.example.todolist.Repository.ToDoRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.domain.Emotion;
import com.example.todolist.domain.EmotionStatus;
import com.example.todolist.domain.ToDo;
import com.example.todolist.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmotionService {

    private final EmotionRepository emotionRepository;
    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;

    @Autowired
    public EmotionService(EmotionRepository emotionRepository,UserRepository userRepository,ToDoRepository toDoRepository){
        this.emotionRepository=emotionRepository;
        this.userRepository=userRepository;
        this.toDoRepository=toDoRepository;
    }

    public void addEmotion(Long userId, Long toDoId){

        ToDo toDo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new CommondException(ExceptionCode.NOT_MYTODO));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));

        if(isExistEmotion(userId,toDoId)){
            throw new CommondException(ExceptionCode.ALREADY_EXIST_EMOTION);
        }
        emotionRepository.save(toEmotion(user,toDo));
    }

    public void deleteEmotion(Long userId, Long toDoId){
        ToDo toDo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new CommondException(ExceptionCode.NOT_MYTODO));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));

        Emotion emotion = emotionRepository.findByUserAndToDo(user,toDo)
                .orElseThrow(() -> new CommondException(ExceptionCode.EMOTION_NOTFOUND));

        emotionRepository.delete(emotion);
    }

    public Long findLikeCnt(Long toDoId){
        ToDo toDo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new CommondException(ExceptionCode.TODO_NOTFOUND));
        return emotionRepository.countByToDo(toDo);
    }

    public boolean isExistEmotion(Long userId, Long toDoId){
        ToDo toDo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new CommondException(ExceptionCode.NOT_MYTODO));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));

        return emotionRepository.existsByUserAndToDo(user,toDo);
    }

    public Emotion toEmotion(User user, ToDo toDo){

        return Emotion.builder()
                .emotionStatus(EmotionStatus.Like) // 일단 Like기능만 구현하기로 했습니다.
                .user(user)
                .toDo(toDo)
                .build();
    }
}
