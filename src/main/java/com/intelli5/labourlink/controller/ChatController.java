package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.ChatMessageDTO;
import com.intelli5.labourlink.entity.ChatMessage;
import com.intelli5.labourlink.entity.ChatNotification;
import com.intelli5.labourlink.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate; // Autowire SimpMessagingTemplate

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        System.out.println("Received message: " + chatMessage);
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                ChatNotification.builder()
                        .id(savedMsg.getId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                        .build()
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessageDTO>> findChatMessages(@PathVariable String senderId,
                                                                 @PathVariable String recipientId) {
        List<ChatMessageDTO> messageHistoryDTO = new ArrayList<>();
        List<ChatMessage> messageHistory = chatMessageService.findChatMessages(senderId, recipientId);

        // Convert ChatMessage objects to ChatMessageDTO objects
        for (ChatMessage message : messageHistory) {
            ChatMessageDTO messageDTO = new ChatMessageDTO(
                    message.getId(),
                    message.getChatId(),
                    message.getSenderId(),
                    message.getRecipientId(),
                    message.getContent(),
                    message.getTimestamp()
            );
            messageHistoryDTO.add(messageDTO);
        }

        return ResponseEntity.ok(messageHistoryDTO);
    }

    @PostMapping("/saveMessage")
    public ResponseEntity<ChatMessage> saveMessage(@RequestBody ChatMessage chatMessage) {
        try {
            ChatMessage savedMessage = chatMessageService.save(chatMessage);
            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/markAsRead/{senderId}/{receiverId}")
    public ResponseEntity<Void> markAsRead(@PathVariable String senderId, @PathVariable String receiverId) {
        try {
            chatMessageService.markAsRead(senderId, receiverId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Handle exceptions appropriately, e.g., log them
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("unreadMessageCount/{senderId}/{receiverId}")
    public ResponseEntity<Integer> unreadMessageCount(@PathVariable String senderId,@PathVariable String receiverId){
        Integer count = chatMessageService.unreadMessageCount(senderId, receiverId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("totalUnreadMessageCount/{senderId}")
    public ResponseEntity<Integer> totalUnreadMessageCount(@PathVariable String senderId){
        Integer count = chatMessageService.totalUnreadMessageCount(senderId);
        return ResponseEntity.ok(count);
    }


}
