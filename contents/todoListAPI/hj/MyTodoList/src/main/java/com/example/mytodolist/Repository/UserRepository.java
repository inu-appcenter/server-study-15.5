package com.example.mytodolist.Repository;

import com.example.mytodolist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
