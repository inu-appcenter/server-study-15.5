package com.example.todolist.Service;

import com.example.todolist.DTO.User.AddUserReqDTO;
import com.example.todolist.DTO.User.ChangeUserReqDTO;
import com.example.todolist.DTO.User.DeleteUserReqDTO;
import com.example.todolist.DTO.User.ReadUserResDTO;
import com.example.todolist.Exception.CommondException;
import com.example.todolist.Exception.ExceptionCode;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.domain.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void addUser(AddUserReqDTO addUserReqDTO){  // 회원가입 로직

        if(userRepository.existsByEmail(addUserReqDTO.getEmail())){ // email값이 중복일 경우
            throw new CommondException(ExceptionCode.EMAIL_DUPLICATED);
        }

        User user = addUserReqDTO.toEntity(addUserReqDTO);
        userRepository.save(user);
    }
    
    public ReadUserResDTO readUserInfo(Long userId){ // 회원 정보조회 로직, userId값은 토큰에서 가져온다.

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));
        return ReadUserResDTO.toDto(user);
    }
    public void changeUserInfo(ChangeUserReqDTO changeUserReqDTO){ // 회원정보 수정 로직

        User user = userRepository.findById(changeUserReqDTO.getUserId())
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));

        if(!user.getPassword().equals(changeUserReqDTO.getOldPassword())){ // 비밀번호가 불일치 할 경우
            throw new CommondException(ExceptionCode.PASSWORD_NOTMATCH);
        }

        userRepository.save(changeUserReqDTO.changeUser(changeUserReqDTO,user));
    }

    @Transactional
    public void deleteUser(DeleteUserReqDTO deleteUserReqDTO){ // 회원 탈퇴 로직

        User user = userRepository.findById(deleteUserReqDTO.getUserId())
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));

        if(!user.getPassword().equals(deleteUserReqDTO.getPassword())){ // 비밀번호가 불일치 할 경우
            throw new CommondException(ExceptionCode.PASSWORD_NOTMATCH);
        }

        userRepository.delete(user);
    }

}
