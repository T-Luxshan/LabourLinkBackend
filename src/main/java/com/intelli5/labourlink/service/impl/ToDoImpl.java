package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.entity.Todo;
import com.intelli5.labourlink.repository.ToDoRepo;
import com.intelli5.labourlink.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoImpl implements TodoService {
    @Autowired
    private ToDoRepo todorepo;

    @Override
    public List<Todo> GetAllToDo() {

        return todorepo.findAll();
    }

    @Override
    public Todo create(Todo todo) {

        return todorepo.save(todo);
    }

    @Override
    public void delete(Long id) throws Exception {
        Todo todo=todorepo.findById(id).orElseThrow(()->new Exception("todo not exist"));
        todorepo.delete(todo);
    }
}