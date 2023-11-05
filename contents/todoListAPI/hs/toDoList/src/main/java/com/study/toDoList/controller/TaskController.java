package com.study.toDoList.controller;

import com.study.toDoList.dto.*;
import com.study.toDoList.service.TaskService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "할일추가",description = "바디에 {title,description,endDate(yyyy-mm-dd)}을 json 형식으로 보내주세요.")
    @ApiResponses({
            @ApiResponse(code = 201,message = "할일추가성공")
    })
    @PostMapping("/api/task")
    public ResponseEntity<?> save(@RequestBody TaskSaveDto taskSaveDto){
        Long id = taskService.save(taskSaveDto);
        return new ResponseEntity<>(new ResponseDto(id,"할일추가성공"), HttpStatus.CREATED);
    }
    @Operation(summary = "할일수정",description = "url 경로변수에 할일아이디,바디에 {title,description,endDate,isFinished(bool)}을 json 형식으로 보내주세요.")
    @ApiResponses({
            @ApiResponse(code = 200,message = "할일수정성공")
    })
    @PutMapping("/api/task/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TaskUpdateDto taskUpdateDto){
        taskService.update(id,taskUpdateDto);
        return new ResponseEntity<>(new ResponseDto(id,"할일수정성공"), HttpStatus.OK);
    }
    @Operation(summary = "할일 가져오기",description = "url 경로변수에 할일아이디를 담아 보내주세요")
    @ApiResponses({
            @ApiResponse(code = 200,message = "할일가져오기성공")
    })
    @GetMapping("/api/task/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable Long id){
        return ResponseEntity.ok().body(taskService.getTask(id));
    }
    @Operation(summary = "할일삭제",description = "url 경로변수에 할일아이디를 보내주세요.")
    @ApiResponses({
            @ApiResponse(code = 204,message = "할일삭제성공")
    })
    @DeleteMapping("/api/task/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        taskService.delete(id);
        return new ResponseEntity<>(new ResponseDto(id,"할일삭제성공"), HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "모든 할일 가져오기",description = "url 경로변수에 회원아이디를 담아 보내주세요")
    @ApiResponses({
            @ApiResponse(code = 200,message = "모든할일가져오기성공")
    })
    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<List<TaskListResponseDto>> getAllTask(@PathVariable Long id){
        return ResponseEntity.ok().body(taskService.getAllTask(id));
    }
}
