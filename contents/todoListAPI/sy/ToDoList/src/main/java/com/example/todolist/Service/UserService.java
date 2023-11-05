package com.example.todolist.Service;

import com.example.todolist.DTO.User.AddUserReqDTO;
import com.example.todolist.DTO.User.ChangeUserReqDTO;
import com.example.todolist.DTO.User.DeleteUserReqDTO;
import com.example.todolist.DTO.User.ReadUserResDTO;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void addUser(AddUserReqDTO addUserReqDTO){  // 회원가입 로직

        if(!userRepository.existsByEmail(addUserReqDTO.getEmail())){
            // 예외처리
        }
        User user = User.builder()
                .name(addUserReqDTO.getName())
                .email(addUserReqDTO.getEmail())
                .password(addUserReqDTO.getPassword())
                .build();

        userRepository.save(user);
    }
    
    public ReadUserResDTO readUserInfo(Long userId){ // 회원 정보조회 로직

        User user = userRepository.findById(userId).orElseThrow( /*예외처리 */);

        ReadUserResDTO readUserResDTO = ReadUserResDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();

        return readUserResDTO;
    }
    @Transactional
    public void changeUserInfo(ChangeUserReqDTO changeUserReqDTO){ // 회원정보 수정 로직

        User user = userRepository.findById(changeUserReqDTO.getUserId()).orElseThrow( /*예외처리 */);
        if(!user.getPassword().equals(changeUserReqDTO.getOldPassword())){
            // 예외처리
        }


        user.changeName(changeUserReqDTO.getName());
        user.changePassword(changeUserReqDTO.getNewPassword());

        userRepository.save(user);
    }

    public void deleteUser(DeleteUserReqDTO deleteUserReqDTO){ // 회원 탈퇴 로직

        User user = userRepository.findById(deleteUserReqDTO.getUserId()).orElseThrow( /*예외처리 */);
        if(!user.getPassword().equals(deleteUserReqDTO.getPassword())){
            // 예외처리
        }

        userRepository.delete(user);
    }

}
