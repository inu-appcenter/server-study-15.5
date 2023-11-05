package com.example.TodoProject;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan("com.example.TodoProject.entity")
public class TodoProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoProjectApplication.class, args);
    }

}
