package com.example.todolist.Service;

import com.example.todolist.DTO.ToDo.AddToDoReqDTO;
import com.example.todolist.Repository.ToDoRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.domain.ToDo;
import com.example.todolist.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository){
        this.toDoRepository=toDoRepository;
        this.userRepository=userRepository;
    }

    public void addToDo(AddToDoReqDTO addToDoReqDTO){ // Todo추가 로직

        User user = userRepository.findById(addToDoReqDTO.getUserId()).orElseThrow(/*예외처리*/);

        ToDo toDo = ToDo.builder()
                .title(addToDoReqDTO.getTitle())
                .content(addToDoReqDTO.getContent())
                .dueDate(addToDoReqDTO.getDueDate())
                .isFinished(addToDoReqDTO.isFinished())
                .user(user)
                .build();

        toDoRepository.save(toDo);
    }
}
