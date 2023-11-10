package com.example.mytodolist.service;


import com.example.mytodolist.Repository.TodoRepository;
import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.TodoRequestDto;
import com.example.mytodolist.dto.TodoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoResponseDto getTodo(Long id)
    {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException());

        TodoResponseDto todoResponseDto = TodoResponseDto.EntityToDto(todo);

        return todoResponseDto;
    }

    public TodoResponseDto saveTodo(Long id, TodoRequestDto todoRequestDto){
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        Todo todo = TodoRequestDto.DtoToEntity(todoRequestDto,user);

        todoRepository.save(todo);

        TodoResponseDto todoResponseDto = TodoResponseDto.EntityToDto(todo);

        return todoResponseDto;
    }

    public TodoResponseDto updateTodo(Long id,TodoRequestDto todoRequestDto){
        Todo todo = todoRepository.findById(id).orElseThrow(()->new NoSuchElementException("업데이트 할 todo가 존재하지 않습니다."));

        todo.updateTodo(todoRequestDto.getTitle(),todoRequestDto.getContent(),todoRequestDto.getDeadLine());
        todoRepository.save(todo);

        TodoResponseDto todoResponseDto =  TodoResponseDto.EntityToDto(todo);

        return todoResponseDto;
    }

    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }

    //todo에서 checkCompleted를 이용하여 필드 값을 바꿔 준 다음, todo를 기반으로 User 객체를 불러와야하는데 안불러짐.. 또 트랜잭션으로 영속을 유지 해야 된다.
    //이 메서드에서 Todo의 isCompleted를 체크함과 동시에 해당 Todo의 작성자의 레벨이 오른다.
    @Transactional
    public TodoResponseDto checkCompleted(Long id,Boolean isCompleted){
        Todo todo = todoRepository.findById(id).orElseThrow(()->new NoSuchElementException("체크 할 todo가 존재하지 않습니다."));

        if(isCompleted){
            todo.checkCompleted();

            User user = todo.getUser();     //여기서 또 영속이 끊김
            user.LevelUp(user.getLevel() + 1);
            userRepository.save(user);
        }
        todoRepository.save(todo);

        TodoResponseDto todoResponseDto =  TodoResponseDto.EntityToDto(todo);

        return todoResponseDto;
    }

}
