package com.example.TodoProject.repository;

import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoGroupRepository extends JpaRepository<TodoGroup, Long> {

    Optional<TodoGroup> findByGroupNum(Long todoGroupNum);
    List<TodoGroup> findByClientClientNum(Long ClientNum);

    Optional<TodoGroup> getByGroupNum(Long todoGroupNum);



}
