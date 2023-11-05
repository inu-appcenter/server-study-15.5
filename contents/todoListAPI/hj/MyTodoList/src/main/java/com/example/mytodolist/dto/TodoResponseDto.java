package com.example.mytodolist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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

}
