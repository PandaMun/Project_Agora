package com.ssafy.config;

import com.ssafy.application.CallHandler;
import com.ssafy.application.room.RoomManager;
import com.ssafy.application.user.UserRegistry;
import com.ssafy.repository.RoomStorage;
import com.ssafy.repository.UserStorage;
import lombok.RequiredArgsConstructor;
import org.kurento.client.KurentoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@RequiredArgsConstructor
public class KurentoConfig implements WebSocketConfigurer {

    private final RoomStorage roomRepository;

    private final UserStorage userStorage;

    @Bean
    public UserRegistry registry() {
        return new UserRegistry(userStorage);
    }

    @Bean
    public RoomManager roomManager() {
        return new RoomManager(kurentoClient(), roomRepository);
    }

    @Bean
    public CallHandler groupCallHandler() {
        return new CallHandler(roomManager(), registry());
    }

    @Bean
    public KurentoClient kurentoClient() {
        return KurentoClient.create();
    }

    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(32768);
        return container;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(groupCallHandler(), "/groupcall")
                .setAllowedOriginPatterns("*");
    }
}
