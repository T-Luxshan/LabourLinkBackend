package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.ChatMessageDTO;
import com.intelli5.labourlink.entity.ChatMessage;
import com.intelli5.labourlink.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(); // You can create your own dedicated exception
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        System.out.println("message is : "+ chatMessage);
        return chatMessage;

    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatIdOptional = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatIdOptional.map(chatId -> repository.findByChatId(chatId)).orElse(new ArrayList<>());
    }

    public ChatMessage saveChatMessage(ChatMessageDTO chatMessageDTO) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessageDTO.getSenderId(), chatMessageDTO.getRecipientId(), true)
                .orElseThrow(); // Create your own dedicated exception

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(chatMessageDTO.getSenderId());
        chatMessage.setRecipientId(chatMessageDTO.getRecipientId());
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessage.setTimestamp(chatMessageDTO.getTimestamp());
        chatMessage.setChatId(chatId);

        repository.save(chatMessage);
        System.out.println("message is : " + chatMessage);
        return chatMessage;
    }
}