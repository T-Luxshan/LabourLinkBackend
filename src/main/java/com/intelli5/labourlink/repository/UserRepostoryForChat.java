package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Status;
import com.intelli5.labourlink.entity.UserEmailAndNameForChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepostoryForChat extends JpaRepository<UserEmailAndNameForChat, String> {
    List<UserEmailAndNameForChat> findAllByStatus(Status status);
}
