package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.ChatMessage;
import com.intelli5.labourlink.repository.ChatMessageRepository;
import com.intelli5.labourlink.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    // Method to save a chat message
    public ChatMessage save(ChatMessage chatMessage){
        var chatId=chatRoomService.getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow();// If the chat room ID is not present, throw an exception
        chatMessage.setChatId(chatId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    // Method to find chat messages between two users
    public List<ChatMessage> findChatMessages(String senderId,String recipientId){
        var chatId=chatRoomService.getChatRoomId(senderId,recipientId,false);
        // If a chat room ID is present, fetch messages, otherwise return an empty list
        return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }
}
