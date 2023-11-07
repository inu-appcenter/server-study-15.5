package com.example.todolist.Service;

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

        User user = userRepository.findById(userId).orElseThrow(/*예외처리*/);
        ToDo toDo= toDoRepository.findById(toDoId).orElseThrow(/*예외처리*/);

        if(isExistEmotion(userId,toDoId)){
            //예외처리
        }
        Emotion emotion = Emotion.builder()
                .emotionStatus(EmotionStatus.Like) // 일단 Like기능만 구현하기로 했습니다.
                .user(user)
                .toDo(toDo)
                .build();
        emotionRepository.save(emotion);
    }

    public void deleteEmotion(Long userId, Long toDoId){
        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        Emotion emotion = emotionRepository.findByUserAndToDo(user,toDo).orElseThrow(/*예외처리*/);

        emotionRepository.delete(emotion);
    }

    public Long findLikeCnt(Long toDoId){
        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow();
        return emotionRepository.countByToDo(toDo);
    }

    public boolean isExistEmotion(Long userId, Long toDoId){
        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        return emotionRepository.existsByUserAndToDo(user,toDo);
    }
}
