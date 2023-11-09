package com.example.TodoProject.repository;

import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByTodoNum(Long todoNum);

    List<Todo> findByClientClientNum(Long ClientNum);

    List<Todo> findByTodoGroupGroupNum(Long todoGroupNum);

}
