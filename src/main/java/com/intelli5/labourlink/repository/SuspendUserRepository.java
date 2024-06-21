package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.SuspendUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuspendUserRepository extends JpaRepository<SuspendUser,String> {

    Optional<SuspendUser> findByEmail(String email);
}
