package com.example.mytodolist.dto;

import com.example.mytodolist.domain.Todo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {
    private Long id;
    private String title;
    private String content;
    private Boolean isCompleted;
    private LocalDateTime deadLine;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public TodoResponseDto(Long id, String title, String content,Boolean isCompleted, LocalDateTime deadLine, LocalDateTime createdDate,LocalDateTime modifiedDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.isCompleted = isCompleted;
        this.deadLine= deadLine;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    //서비스에서 사용하던 변환 메서스를 dto로 옮김.
    public static TodoResponseDto convertEntityToDto(Todo todo)
    {

        return TodoResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .content(todo.getContents())
                .isCompleted(todo.getCompleted())
                .deadLine(todo.getDeadLine())
                .createdDate(todo.getCreatedDate())
                .modifiedDate(todo.getModifiedDate())
                .build();
    }

}
