package com.example.mytodolist.dto;

import com.example.mytodolist.domain.Todo;
import com.example.mytodolist.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor // Json @RequestBody 형식으로 데이터를 입력받고, 객체화 하려면 dto에 기본 생성자가 있어야한다.
public class TodoRequestDto {

    @NotBlank(message = "할 일 제목을 꼭 입력해주세요")
    @ApiModelProperty(value = "할 일 제목", example = "나의 하루 루틴")
    @Size(min =1, max = 20)
    private String title;
    
    @NotBlank(message = "할 일 내용을 꼭 입력해주세요")
    @ApiModelProperty(value = "할일 내용", example = "1.독서하기, 2.영화보기")
    @Size(min = 1, max = 100)
    private String content;
    
    @ApiModelProperty(value = "마감 기한", example = "2023-12-31T23:59:59")
    @Pattern(regexp = "(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])[T](2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]$")
    private String deadLine;


    @Builder
    public TodoRequestDto(String title, String content,String deadLine)
    {
        this.title = title;
        this.content = content;
        this.deadLine = deadLine;
    }

    //서비스에서 사용하던 변환 메서스를 dto로 옮김.
    public static Todo convertDtoToEntity(TodoRequestDto todoRequestDto, User user)
    {
        
        return Todo.builder()
                .title(todoRequestDto.getTitle())
                .contents(todoRequestDto.getContent())
                .deadLine(stringToTime(todoRequestDto.getDeadLine()))
                .user(user)
                .build();
    }

    //String으로 deadLine을 받았기 떄문에 이를 LocalDateTime으로 변환해줌.(유효성 검사를 하기 위해 String으로 받았다)
    public static LocalDateTime stringToTime(String deadLine){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime formatDateTime  = LocalDateTime.parse(deadLine,format);

        return formatDateTime;
    }

}
