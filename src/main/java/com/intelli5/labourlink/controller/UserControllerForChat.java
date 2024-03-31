package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.UserEmailAndNameForChat;
import com.intelli5.labourlink.service.UserServiceForChat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserControllerForChat {
    private final UserServiceForChat userServiceForChat;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UserEmailAndNameForChat addUser(
            @Payload UserEmailAndNameForChat user
    ) {
        userServiceForChat.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UserEmailAndNameForChat disconnectUser(
            @Payload UserEmailAndNameForChat user
    ) {
        userServiceForChat.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEmailAndNameForChat>> findConnectedUsers() {
        return ResponseEntity.ok(userServiceForChat.findConnectedUsers());
    }
}
