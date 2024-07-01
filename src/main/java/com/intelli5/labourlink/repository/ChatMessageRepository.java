package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findByChatId(String s);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.senderId = :senderId AND cm.recipientId = :receiverId")
    List<ChatMessage> findChatMessagesBySenderIdAndReceiverId(String senderId, String receiverId);
}