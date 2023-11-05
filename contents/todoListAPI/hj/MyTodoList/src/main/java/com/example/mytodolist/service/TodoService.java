package com.example.mytodolist.service;


import com.example.mytodolist.Repository.TodoRepository;
import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.TodoRequestDto;
import com.example.mytodolist.dto.TodoResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    private TodoResponseDto convertEntityToDto(Todo todo){
        return TodoResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .content(todo.getContents())
                .isCompleted(todo.getIsCompleted())
                .deadLine(todo.getDeadLine())
                .createdDate(todo.getCreateDate())
                .modifiedDate(todo.getModifiedDate())
                .build();
    }

    public TodoResponseDto getTodo(Long id)
    {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException());

        TodoResponseDto todoResponseDto = this.convertEntityToDto(todo);

        return todoResponseDto;
    }

    public TodoResponseDto saveTodo(Long id, TodoRequestDto todoRequestDto){
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        Todo todo = Todo.builder()
                .title(todoRequestDto.getTitle())
                .contents(todoRequestDto.getContent())
                .deadLine(todoRequestDto.getDeadLine())
                .build();
        todo.assignToUser(user);
        todo.checkCompleted(false);
        todo.updateModifiedDate(LocalDateTime.now());
        todo.setCreateDate(LocalDateTime.now());

        todoRepository.save(todo);

        TodoResponseDto todoResponseDto = this.convertEntityToDto(todo);

        return todoResponseDto;
    }

    public TodoResponseDto updateTodo(Long id,TodoRequestDto todoRequestDto){
        Todo todo = todoRepository.findById(id).orElseThrow(()->new NoSuchElementException("업데이트 할 todo가 존재하지 않습니다."));

        todo.updateTodo(todoRequestDto.getTitle(),todoRequestDto.getContent(),todoRequestDto.getDeadLine());
        todo.updateModifiedDate(LocalDateTime.now());

        todoRepository.save(todo);

        TodoResponseDto todoResponseDto = this.convertEntityToDto(todo);


        return todoResponseDto;
    }


    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }

    @Transactional
    public TodoResponseDto checkCompleted(Long id,String isCompleted){
        Todo todo = todoRepository.findById(id).orElseThrow(()->new NoSuchElementException("체크 할 todo가 존재하지 않습니다."));

        if(isCompleted.equals("Completed")){
            todo.checkCompleted(true);

            User user = todo.getUser();
            user.checkLevel(user.getLevel() + 1);
            userRepository.save(user);
        }
        else{
            todo.checkCompleted(false);
        }

        todoRepository.save(todo);
        todo.updateModifiedDate(LocalDateTime.now());

        TodoResponseDto todoResponseDto = this.convertEntityToDto(todo);


        return todoResponseDto;
    }






}
