package com.example.mytodolist.dto;

import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // Json @RequestBody 형식으로 데이터를 입력받고, 객체화 하려면 dto에 기본 생성자가 있어야한다.
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

    //서비스에서 사용하던 변환 메서스를 dto로 옮김.
    public static Todo DtoToEntity(TodoRequestDto todoRequestDto, User user)
    {
        return Todo.builder()
                .title(todoRequestDto.getTitle())
                .contents(todoRequestDto.getContent())
                .deadLine(todoRequestDto.getDeadLine())
                .user(user)
                .build();
    }

}
