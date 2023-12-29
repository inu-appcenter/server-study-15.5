package com.example.mytodolist.Repository;

import com.example.mytodolist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    // 현재 id 값은 인덱스 값이기 때문에 id값을 토큰 생성 정보로 사용하기 위해 getByUid() 생성
    Optional<User> getByUid(String uid);

    //중복되는 아이디를 체크하기 위함.
    boolean existsByUid(String uid);

}
