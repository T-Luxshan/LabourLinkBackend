package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}