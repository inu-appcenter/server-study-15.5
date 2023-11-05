package com.example.todo.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByUser_Id(Long userId);
}
