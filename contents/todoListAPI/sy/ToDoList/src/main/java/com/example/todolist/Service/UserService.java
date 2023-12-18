package com.example.todolist.Service;

import com.example.todolist.DTO.User.*;
import com.example.todolist.Exception.CommondException;
import com.example.todolist.Exception.ExceptionCode;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.Security.JwtProvider;
import com.example.todolist.domain.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.jwtProvider=jwtProvider;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional
    public LoginResDTO login(LoginReqDTO loginReqDTO){

        User user = userRepository.findByEmail(loginReqDTO.getEmail())
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));

        isMatchPassword(loginReqDTO.getPassword(), user.getPassword());

        List<GrantedAuthority> roleList = new ArrayList<>();
        roleList.add(new SimpleGrantedAuthority(user.getRoleName()));

        String accessToken = jwtProvider.createToken(user.getUserId(), roleList);

        return LoginResDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    public void addUser(AddUserReqDTO addUserReqDTO){  // 회원가입 로직

        if(userRepository.existsByEmail(addUserReqDTO.getEmail())){ // email값이 중복일 경우
            throw new CommondException(ExceptionCode.EMAIL_DUPLICATED);
        }
        addUserReqDTO.setPassword(passwordEncoder.encode(addUserReqDTO.getPassword()));

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

        isMatchPassword(changeUserReqDTO.getOldPassword(), user.getPassword());

        userRepository.save(changeUserReqDTO.changeUser(changeUserReqDTO,user));
    }

    @Transactional
    public void deleteUser(DeleteUserReqDTO deleteUserReqDTO){ // 회원 탈퇴 로직

        User user = userRepository.findById(deleteUserReqDTO.getUserId())
                .orElseThrow(() -> new CommondException(ExceptionCode.USER_NOTFOUND));

        isMatchPassword(deleteUserReqDTO.getPassword(), user.getPassword());

        userRepository.delete(user);
    }

    public void isMatchPassword(String password1, String password2){

        if(!passwordEncoder.matches(password1,password2))
            throw new CommondException(ExceptionCode.PASSWORD_NOTMATCH);
    }

}
