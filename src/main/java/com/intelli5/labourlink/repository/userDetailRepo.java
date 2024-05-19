package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface userDetailRepo extends JpaRepository<User,String> {


    User findByEmail(String email);



}
