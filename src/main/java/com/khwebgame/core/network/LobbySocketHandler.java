package com.khwebgame.core.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khwebgame.config.Config;
import com.khwebgame.core.protocol.LobbyProtocol;
import com.khwebgame.core.protocol.LobbyProtocolImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class LobbySocketHandler extends TextWebSocketHandler {
    LobbyProtocol lobbyProtocol = new LobbyProtocolImpl();
    List<WebSocketSession>  sessions = new ArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        System.out.println(message.getPayload());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(message.getPayload(), new TypeReference<Map<String, String>>(){});

        handler(session, map);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println(session.getId() + " connected");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println(session.getId() + " disconnected");
    }

    private boolean handler(final WebSocketSession session, final Map<String, Object> mapData) throws Exception {
        if (!mapData.containsKey(Config.PROTOCOL_PREFIX))
            return false;

        LobbyProtocol.TYPE proto = LobbyProtocol.TYPE.values()[Integer.parseInt(mapData.get(Config.PROTOCOL_PREFIX).toString())];
        switch (proto) {
            case ROOM_CREATE: {
                if (!mapData.containsKey("roomName")) {
                    failHandler(session, "roomName");
                    return false;
                }

                lobbyProtocol.roomCreate(session, mapData.get("roomName").toString());
                return true;
            }
            case ROOM_JOIN: {
                if (!mapData.containsKey("uid")) {
                    failHandler(session, "uid");
                    return false;
                }

                lobbyProtocol.roomJoin(session, UUID.fromString(mapData.get("uid").toString()));
            }

            case ROOMINFO_UPDATE: {
                lobbyProtocol.roomInfoUpdateResponse(session);
                return true;
            }
        }

        return false;
    }

    private void failHandler(final WebSocketSession session, final String tag) throws Exception {
        session.sendMessage(new TextMessage(
                "{" + Config.PROTOCOL_SUC + ":false, tag:" + tag + "}"));

        System.out.println("send to user that a message");
    }

    public void roomInfoUpdate() throws Exception {
        for (WebSocketSession session : sessions) {
            lobbyProtocol.roomInfoUpdateResponse(session);
        }
    }
}
