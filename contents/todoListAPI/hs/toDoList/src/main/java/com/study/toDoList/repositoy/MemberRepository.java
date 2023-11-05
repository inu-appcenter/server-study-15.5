package com.study.toDoList.repositoy;

import com.study.toDoList.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
