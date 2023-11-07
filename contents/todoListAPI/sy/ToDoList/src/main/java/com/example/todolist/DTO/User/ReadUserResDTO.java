package com.example.todolist.DTO.User;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserResDTO {

    private String email;

    private String name;
}
