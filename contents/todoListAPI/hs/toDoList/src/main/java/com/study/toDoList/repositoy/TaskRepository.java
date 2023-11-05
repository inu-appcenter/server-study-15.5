package com.study.toDoList.repositoy;

import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByMember(Member member);
}
