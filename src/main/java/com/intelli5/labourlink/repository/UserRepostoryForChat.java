package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Status;
import com.intelli5.labourlink.entity.UserEmailAndNameForChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepostoryForChat extends JpaRepository<UserEmailAndNameForChat, String> {
    List<UserEmailAndNameForChat> findAllByStatus(Status status);
}
