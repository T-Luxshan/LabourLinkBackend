package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.AdminProfileDTO;
import com.intelli5.labourlink.entity.ApiResponse;
import com.intelli5.labourlink.entity.Todo;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.service.UserService;
import com.intelli5.labourlink.service.impl.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apptodo")
@CrossOrigin(origins = "*")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @GetMapping("/api")
    public ApiResponse homeController(){
        ApiResponse res=new ApiResponse();
        res.setMessage("To Do delete successfully");
        res.setStatus(true);
        return res;
    }
    @PostMapping("/")
    public Todo create(@RequestBody Todo todo ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User userDetails = (User) principal;
            String email = userDetails.getEmail();
            todo.setAdminEmail(email);
        }
        return toDoService.create(todo);
    }
    @GetMapping("/api/todo")
    public List<Todo> GetAllToDo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = null;
        if (principal instanceof User) {
            User userDetails = (User) principal;
            email = userDetails.getEmail();
        }
        return toDoService.getTodoByAdminEmail(email);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) throws Exception {
        toDoService.delete(id);
        ApiResponse res=new ApiResponse();
        res.setMessage("To Do delete successfully");
        res.setStatus(true);
        return res;
    }
    @PutMapping("/{id}")
    public Boolean checkToDo(@PathVariable Long id) throws Exception {
        return toDoService.checkToDo(id);
    }

}