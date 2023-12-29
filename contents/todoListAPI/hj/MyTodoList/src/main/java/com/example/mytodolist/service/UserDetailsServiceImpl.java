package com.example.mytodolist.service;

import com.example.mytodolist.Repository.UserRepository;
import com.example.mytodolist.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
//UserDetails는 스프링 시큐리티에서 제공하는 개념으로, UserDetails의 username은 각 사용자를 구분할 수 있는 ID를 의미합니다.
//UserDetailService 인터페이스의 메서드를 보면 username을 가지고 UserDetails 객체를 리턴하게끔 정의 돼 있는데,
//UserDetails의 구현체로 User 엔티티를 생성했기 때문에 User 객체를 리턴하게끔 구현한 것 입니다.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        log.info("[loadUserByUsername] loadUserByUsername 수행. username: {}",username);
        User user = userRepository.getByUid(username).orElseThrow(()->new NoSuchElementException("유저가 존재하지 않습니다."));
        return user;
    }
}
