package com.example.todolist.DTO.User;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserReqDTO {

    private Long userId; // userId값은 토큰에서 가져온다.

    private String password;
}
