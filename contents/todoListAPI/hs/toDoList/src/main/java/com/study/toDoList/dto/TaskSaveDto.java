package com.study.toDoList.dto;

import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskSaveDto {
    @NotBlank
    private String title;
    @NotEmpty
    private String description;
    @Future
    private LocalDateTime endDate;

    @Builder
    public TaskSaveDto(String title, String description, LocalDateTime endDate){
        this.title = title;
        this.description = description;
        this.endDate = endDate;
    }

    public Task toEntity(){
        return Task.builder()
                .title(title)
                .description(description)
                .endDate(endDate)
                .build();
    }
}
