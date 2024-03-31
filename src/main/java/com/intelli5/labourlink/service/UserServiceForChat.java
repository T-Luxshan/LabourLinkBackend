package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Status;
import com.intelli5.labourlink.entity.UserEmailAndNameForChat;
import com.intelli5.labourlink.repository.UserRepostoryForChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceForChat {
    private final UserRepostoryForChat userRepostoryForChat;

    @Autowired
    public UserServiceForChat(UserRepostoryForChat userRepostoryForChat) {
        this.userRepostoryForChat = userRepostoryForChat;
    }

    public void saveUser(UserEmailAndNameForChat user) {
        user.setStatus(Status.ONLINE);
        userRepostoryForChat.save(user);
    }

    public void disconnect(UserEmailAndNameForChat user) {
        var storedUser = userRepostoryForChat.findById(user.getEmail()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            userRepostoryForChat.save(storedUser);
        }
    }

    public List<UserEmailAndNameForChat> findConnectedUsers() {
        return userRepostoryForChat.findAllByStatus(Status.ONLINE);
    }
}
