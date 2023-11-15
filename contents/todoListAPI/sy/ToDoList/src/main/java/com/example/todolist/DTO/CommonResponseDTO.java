package com.example.todolist.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class CommonResponseDTO<D> {

    private final String statusCode;
    private final String message;
    private final D data;

}
