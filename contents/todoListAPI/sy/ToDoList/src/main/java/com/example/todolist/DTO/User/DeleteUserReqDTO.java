package com.example.todolist.DTO.User;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserReqDTO {

    private Long userId;

    private String password;
}
