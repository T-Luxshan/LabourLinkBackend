package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.ApiResponse;
import com.intelli5.labourlink.entity.Todo;
import com.intelli5.labourlink.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apptodo")
@CrossOrigin(origins = "*")
public class ToDoController {
    @Autowired
    private TodoService todoservice;

    @GetMapping("/api")
    public ApiResponse homeController(){
        ApiResponse res=new ApiResponse();
        res.setMessage("To Do delete successfully");
        res.setStatus(true);
        return res;
    }

    @GetMapping("/api/todo")
    public List<Todo> GetAllToDo(){
        return todoservice.GetAllToDo();
    }
    @PostMapping("/")
    public Todo create(@RequestBody Todo todo ){
        return todoservice.create(todo);
    }
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) throws Exception {
        todoservice.delete(id);
        ApiResponse res=new ApiResponse();
        res.setMessage("To Do delete successfully");
        res.setStatus(true);
        return res;
    }

}