package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String username);
}
