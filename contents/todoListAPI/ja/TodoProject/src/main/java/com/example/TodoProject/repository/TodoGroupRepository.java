package com.example.TodoProject.repository;

import com.example.TodoProject.entity.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoGroupRepository extends JpaRepository<TodoGroup, Long> {
}
