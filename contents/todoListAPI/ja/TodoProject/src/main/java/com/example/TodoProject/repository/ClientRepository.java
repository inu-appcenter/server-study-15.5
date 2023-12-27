package com.example.TodoProject.repository;

import com.example.TodoProject.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();

    Optional<Client> findByClientId(String clientId);

    Optional<Client> findByClientNum(Long clientNum);

    Optional<Client> findByTodoGroupGroupNum(Long TodoGroupNum);

    UserDetails getByClientId(String clientId);

}
