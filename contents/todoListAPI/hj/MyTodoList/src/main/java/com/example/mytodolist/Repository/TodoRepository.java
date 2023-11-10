package com.example.mytodolist.Repository;

import com.example.mytodolist.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {
}
