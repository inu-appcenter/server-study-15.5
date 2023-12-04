package com.study.toDoList.dto;

import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TaskSaveDto {
    @NotBlank
    private String title;
    @NotNull
    private String description;
    @FutureOrPresent
    private LocalDate endDate;

    @Builder
    public TaskSaveDto(String title, String description, LocalDate endDate){
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
