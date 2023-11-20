package com.example.mytodolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyTodoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTodoListApplication.class, args);
    }

}
