package com.example.TodoProject.dto;

import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
public class RequestTodoGroupDto {

    @Schema(example = "중요한 일 모음")
    private String groupName;

    @Schema(example = "true")
    private Boolean isImportant;

    @Builder
    public RequestTodoGroupDto(String groupName, Boolean isImportant){
        this.groupName = groupName;
        this.isImportant = isImportant;
    }
    public TodoGroup toEntity(Client client, RequestTodoGroupDto requestTodoGroupDto) {
        return TodoGroup.builder()
                .client(client)
                .groupName(getGroupName()) // 사용자로부터 그룹 이름 가져오기
                .isImportant(getIsImportant()) // 사용자로부터 중요성 정보 가져오기
                .build();
    }
}