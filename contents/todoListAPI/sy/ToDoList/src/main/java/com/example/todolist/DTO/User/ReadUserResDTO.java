package com.example.todolist.DTO.User;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserResDTO {

    private String email;

    private String name;
}
