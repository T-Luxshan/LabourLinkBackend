package com.intelli5.labourlink.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");//This endpoint allows clients to connect to the server using WebSocket for real-time communication.
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();//provides a fallback mechanism for environments where WebSocket is not supported
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user","/topic");//clients subscribed to destinations starting with "/user" will receive messages from the broker.
        registry.setApplicationDestinationPrefixes("/app");//When clients send messages, they will start with "/app". This helps the server know how to handle incoming messages based on their destination.
        registry.setUserDestinationPrefix("/user");//if a user sends a message to a destination starting with "/user", it will be handled in a special way, likely for direct user-to-user communication.
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver=new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);//It decides that the default type of messages will be in JSON format.

        MappingJackson2MessageConverter converter=new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());//translate messages between Java (what the server understands) and JSON (what the web browsers understand). It's like a translator between two languages.
        converter.setContentTypeResolver(resolver);

        messageConverters.add(converter);
        return false;
    }

}
