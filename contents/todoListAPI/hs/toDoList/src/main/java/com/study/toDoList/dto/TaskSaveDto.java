package com.study.toDoList.dto;

import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskSaveDto {
    private String title;
    private String description;
    private LocalDateTime endDate;
    //private Member member;

    @Builder
    public TaskSaveDto(String title, String description, LocalDateTime endDate/*, Member member*/){
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        //this.member = member;
    }

    public Task toEntity(){
        return Task.builder()
                .title(title)
                .description(description)
                .endDate(endDate)
                /*.member()*/
                .build();
    }
}
