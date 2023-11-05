package com.example.mytodolist.service;

import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.TodoResponseDto;
import com.example.mytodolist.dto.UserRequestDto;
import com.example.mytodolist.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private UserResponseDto convertEntityToDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .level(user.getLevel())
                .build();
    }

    public UserResponseDto getUser(Long id){

        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("유저가 존재하지 않습니다.")) ;

        UserResponseDto userResponseDto = this.convertEntityToDto(user);

        return userResponseDto;
    }

    public UserResponseDto saveUser(UserRequestDto userRequestDto){
        User user;
        user = User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .build();

        userRepository.save(user);

        UserResponseDto userResponseDto = this.convertEntityToDto(user);

        return userResponseDto;
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto){
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 아이디가 존재하지 않습니다."));

        user.updateUserInfo(userRequestDto.getName(), userRequestDto.getEmail());

        userRepository.save(user);

        UserResponseDto userResponseDto = this.convertEntityToDto(user);

        return userResponseDto;
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public List<TodoResponseDto> getTodosByUserId(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        List<Todo> todos = user.getTodoList();

        List<TodoResponseDto> todoDtoList = new ArrayList<>();

        for(Todo todo: todos){
            todoDtoList.add(convertTodoEntityToDto(todo));
        }

        return todoDtoList;
    }

    private TodoResponseDto convertTodoEntityToDto(Todo todo){
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
}
