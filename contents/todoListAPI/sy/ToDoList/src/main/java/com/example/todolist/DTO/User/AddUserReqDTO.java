package com.example.todolist.DTO.User;

import lombok.*;
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddUserReqDTO {

    private String email;

    private String name;

    private String password;
}
