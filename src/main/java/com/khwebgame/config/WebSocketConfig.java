package com.khwebgame.config;

import com.khwebgame.core.network.LobbySocketHandler;
import com.khwebgame.core.network.RoomSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new LobbySocketHandler(), "/khwebgame-lobby")
                .addInterceptors(new HttpHandshakeInterceptor())
                .withSockJS();

        registry.addHandler(new RoomSocketHandler(), "/khwebgame-room")
                .addInterceptors(new RoomHttpHandshakeInterceptor())
                .withSockJS();
        /*
        registry.addHandler(getMyWebSocketHandler(), "/khwebgame-game")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS();
        */
    }
}