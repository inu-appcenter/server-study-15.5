package com.example.TodoProject.dto;

import com.example.TodoProject.common.CommonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommonResponseDto<T> {

    @Schema(example = "SUCCESS")
    private final CommonResponse commonResponse;

    @Schema(example = "성공")
    private final String message;

    @Schema(example = "반환 될 값")
    private final T data;

    public CommonResponseDto(CommonResponse commonResponse, String message, T data) {
        this.commonResponse = commonResponse;
        this.message = message;
        this.data = data;
    }

}
