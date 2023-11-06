package com.example.todolist.Repository;

import com.example.todolist.domain.Emotion;
import com.example.todolist.domain.ToDo;
import com.example.todolist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmotionRepository extends JpaRepository<Emotion,Long> {
    Long countByToDo(ToDo toDo);

    boolean existsByUserAndToDo(User user, ToDo toDo);

    Optional<Emotion> findByUserAndToDo(User user, ToDo toDo);
}
