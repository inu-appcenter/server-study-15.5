package com.example.todolist.Repository;

import com.example.todolist.domain.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion,Long> {

}
