package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> GetAllToDo();
    Todo create (Todo todo);
    void delete (Long id) throws Exception;
}

