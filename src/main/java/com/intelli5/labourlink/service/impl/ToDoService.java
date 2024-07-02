package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.entity.Todo;
import com.intelli5.labourlink.repository.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService  {
    @Autowired
    private ToDoRepo todorepo;

    public List<Todo> getTodoByAdminEmail(String email) {

        return todorepo.findByAdminEmail(email);
    }
    public Todo create(Todo todo) {
        return todorepo.save(todo);
    }

    public void delete(Long id) throws Exception {
        Todo todo=todorepo.findById(id).orElseThrow(()->new Exception("todo not exist"));
        todorepo.delete(todo);
    }
    public  Boolean checkToDo(Long id) throws Exception {
        Todo todo=todorepo.findById(id).orElseThrow(()->new Exception("todo not exist"));
        todo.setIsDone(!todo.getIsDone());
        todorepo.save(todo);
        return true;
    }

}