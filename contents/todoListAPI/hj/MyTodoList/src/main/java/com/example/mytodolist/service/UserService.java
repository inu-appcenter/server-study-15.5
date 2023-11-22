package com.example.mytodolist.service;

import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import com.example.mytodolist.dto.TodoResponseDto;
import com.example.mytodolist.dto.UserRequestDto;
import com.example.mytodolist.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUser(Long id){

        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("유저가 존재하지 않습니다.")) ;

        UserResponseDto userResponseDto = UserResponseDto.convertEntityToDto(user);

        return userResponseDto;
    }

    public UserResponseDto saveUser(UserRequestDto userRequestDto){
        User user;
        user = UserRequestDto.convertDtoToEntity(userRequestDto);
        userRepository.save(user);

        UserResponseDto userResponseDto = UserResponseDto.convertEntityToDto(user);

        return userResponseDto;
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto){
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 아이디가 존재하지 않습니다."));
        user.updateUserInfo(userRequestDto.getName(), userRequestDto.getEmail());
        userRepository.save(user);

        UserResponseDto userResponseDto = UserResponseDto.convertEntityToDto(user);

        return userResponseDto;
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    //repository에서 List형식으로 가져오면 영속이 끊긴 상태로 가져오기 때문에 Transaction으로 영속을 유지시켜준다.
    //읽기만 하기 때문에 readOnly 설정
    @Transactional(readOnly = true) 
    public List<TodoResponseDto> getTodosByUserId(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        List<Todo> todos = user.getTodoList();
        // stream 형식으로 사용자에게서 얻은 todos 들을  TodoReponseDto 형식의 리스트로 변환하여 리턴
        return todos.stream().map(TodoResponseDto::convertEntityToDto).collect(Collectors.toList());
    }

}
