package com.study.toDoList.repositoy;

import com.study.toDoList.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
