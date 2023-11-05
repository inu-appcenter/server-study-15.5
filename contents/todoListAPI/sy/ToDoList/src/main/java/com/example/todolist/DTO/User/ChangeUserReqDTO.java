package com.example.todolist.DTO.User;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserReqDTO {

    private Long userId;

    private String name;

    private String oldPassword;

    private String newPassword;
}
