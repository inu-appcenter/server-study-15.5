package com.example.TodoProject.repository;

import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.Todo;
import com.example.TodoProject.entity.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByTodoNum(Long todoNum);

    List<Todo> findAllByClientClientNumAndTodoTitleContains(Long clientNum, String keyword);

    List<Todo> findByClientClientNumAndTodoGroup(Long clientNum,String todoGroup);

}
