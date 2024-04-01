package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User,String> {

}
