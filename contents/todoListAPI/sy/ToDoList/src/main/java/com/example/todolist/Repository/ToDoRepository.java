package com.example.todolist.Repository;

import com.example.todolist.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ToDoRepository extends JpaRepository<ToDo,Long> {
}
