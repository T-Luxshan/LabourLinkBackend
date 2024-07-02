package com.intelli5.labourlink.repository;


import com.intelli5.labourlink.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepo extends JpaRepository<Todo,Long> {
    List<Todo> findByAdminEmail(String adminEmail);
}
