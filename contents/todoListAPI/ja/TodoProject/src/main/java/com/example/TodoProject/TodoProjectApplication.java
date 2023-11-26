package com.example.TodoProject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@OpenAPIDefinition(servers = {@Server(url = "http://jeonga:5154", description = "기본서버"),
                                @Server(url = "/", description = "Development Server URL")})
@EnableJpaAuditing
@SpringBootApplication
public class TodoProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoProjectApplication.class, args);
    }

}
