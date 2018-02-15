package com.khwebgame.config;

import com.khwebgame.core.model.Room;
import com.khwebgame.core.network.GameSocketHandler;
import com.khwebgame.core.network.LobbySocketHandler;
import com.khwebgame.core.network.RoomSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    LobbySocketHandler lobbySocketHandler;
    @Autowired
    RoomSocketHandler roomSocketHandler;
    @Autowired
    GameSocketHandler gameSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(lobbySocketHandler, "/khwebgame-lobby")
                .setAllowedOrigins("*")
                .addInterceptors(new LobbyHttpHandshakeInterceptor())
                .withSockJS();

        registry.addHandler(roomSocketHandler, "/khwebgame-room")
                .setAllowedOrigins("*")
                .addInterceptors(new RoomHttpHandshakeInterceptor())
                .withSockJS();

        registry.addHandler(gameSocketHandler, "/khwebgame-game")
                .setAllowedOrigins("*")
                .addInterceptors(new RoomHttpHandshakeInterceptor())
                .withSockJS();
    }

    @Bean
    LobbySocketHandler lobbySocketHandler() {
        return new LobbySocketHandler();
    }

    @Bean
    RoomSocketHandler roomSocketHandler() {
        return new RoomSocketHandler();
    }

    @Bean
    GameSocketHandler gameSocketHandler() { return new GameSocketHandler(); }

    @Scheduled(cron = "*/5 * * * * *")
    public void lobby() throws Exception {
        lobbySocketHandler.roomInfoUpdate();
    }
}