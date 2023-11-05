package com.example.mytodolist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TodoRequestDto {

    private String title;
    private String content;
    private LocalDateTime deadLine;

    @Builder
    public TodoRequestDto(String title, String content,LocalDateTime deadLine)
    {
        this.title = title;
        this.content = content;
        this.deadLine = deadLine;
    }

}
