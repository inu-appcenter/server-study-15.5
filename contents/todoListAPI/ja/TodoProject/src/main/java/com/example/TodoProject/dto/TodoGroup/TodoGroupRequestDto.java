package com.example.TodoProject.dto.TodoGroup;

import com.example.TodoProject.entity.Client;
import com.example.TodoProject.entity.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class TodoGroupRequestDto {

    @Getter
    public static class RequestTodoGroupDto {

        @NotBlank(message = "투두 그룹의 이름은 필수 요소입니다.")
        @Schema(description = "투두 그룹의 이름", example = "중요한 일 모음")
        private String groupName;


        @Schema(description = "투두 그룹이 중요한가 아닌가에 대한 여부.",example = "true")
        private Boolean isImportant;

        public TodoGroup toEntity(Client client, RequestTodoGroupDto requestTodoGroupDto) {
            return TodoGroup.builder()
                    .client(client)
                    .groupName(getGroupName())
                    .isImportant(getIsImportant())
                    .build();
        }
    }

}
