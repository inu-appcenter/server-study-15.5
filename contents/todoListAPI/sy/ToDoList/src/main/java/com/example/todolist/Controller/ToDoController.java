package com.example.todolist.Controller;

import com.example.todolist.DTO.ToDo.AddToDoReqDTO;
import com.example.todolist.DTO.ToDo.ReadToDoPreviewResDTO;
import com.example.todolist.DTO.ToDo.ReadToDoResDTO;
import com.example.todolist.DTO.ToDo.UpdateToDoReqDTO;
import com.example.todolist.Service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService){
        this.toDoService=toDoService;
    }

    @GetMapping("/to-dos")
    public ResponseEntity<Object> readToDoPreviewList(){
        List<ReadToDoPreviewResDTO> readToDoPreviewResDTOList = toDoService.readToDoPreviewList();
        return ResponseEntity.status(200).body(readToDoPreviewResDTOList);
    }

    @GetMapping("/to-dos/{toDoId}")
    public ResponseEntity<Object> readToDo(@PathVariable Long toDoId){
        /*
            토큰에서 userId값 추출하는 로직
        */
        Long userId = 2l;

        ReadToDoResDTO readToDoResDTO = toDoService.readToDo(toDoId,userId);
        return ResponseEntity.status(200).body(readToDoResDTO);
    }

    @PostMapping("/to-dos")
    public ResponseEntity<Object> addToDo(@RequestBody AddToDoReqDTO addToDoReqDTO){
        /*
            토큰에서 userId값 추출하는 로직
        */
        Long userId = 2l;
        addToDoReqDTO.setUserId(userId);

        toDoService.addToDo(addToDoReqDTO);
        return ResponseEntity.status(201).body(null);
    }

    @PatchMapping("/to-dos/{toDoId}")
    public ResponseEntity<Object> updateToDo(@PathVariable Long toDoId, @RequestBody UpdateToDoReqDTO updateToDoReqDTO){
        /*
            토큰에서 userId값 추출하는 로직
        */
        Long userId = 2l;
        updateToDoReqDTO.setUserId(userId);
        updateToDoReqDTO.setToDoId(toDoId);

        toDoService.updateToDo(updateToDoReqDTO);
        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/to-dos/{toDoId}")
    public ResponseEntity<Object> deleteToDo(@PathVariable Long toDoId){
        /*
            토큰에서 userId값 추출하는 로직
        */
        Long userId = 2l;

        toDoService.deleteToDo(toDoId,userId);
        return ResponseEntity.status(200).body(null);
    }
}
