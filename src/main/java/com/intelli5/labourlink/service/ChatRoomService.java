package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.ChatRoom;
import com.intelli5.labourlink.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // Method to retrieve or create a chat room ID between two users
    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId) // Find existing chat room by sender and recipient
                .map(ChatRoom::getChatId) // Map the found chat room to its ID
                .or(() -> {
                    if(createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, recipientId); // Create new chat room ID if not exists
                        return Optional.of(chatId);
                    }

                    return  Optional.empty(); // Return empty optional if not creating new room
                });
    }

    // Method to create a new chat room and save it to the repository
    private String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId); // Generate chat room ID

        // Create chat room entities for sender-recipient and recipient-sender combinations
        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient); // Save sender-recipient chat room
        chatRoomRepository.save(recipientSender); // Save recipient-sender chat room

        return chatId; // Return the generated chat room ID
    }
}