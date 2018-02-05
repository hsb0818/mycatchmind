package com.khwebgame.core.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import com.khwebgame.core.protocol.LobbyProtocol;
import com.khwebgame.core.protocol.LobbyProtocolImpl;
import com.khwebgame.core.protocol.RoomProtocol;
import com.khwebgame.core.protocol.RoomProtocolImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class RoomSocketHandler extends TextWebSocketHandler {
    RoomProtocol roomProtocol = new RoomProtocolImpl();
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

        String sessId = session.getAttributes().get(Config.SESS_USER_ID).toString();
        UUID roomUid = UUID.fromString(session.getAttributes().get(Config.SESS_ROOM_UID).toString());
        RoomMng.getInstance().updateWebSocketSession(roomUid, session, sessId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println(session.getId() + " disconnected");
    }

    private boolean handler(final WebSocketSession session, final Map<String, Object> mapData) throws Exception {
        if (mapData.containsKey(Config.PROTOCOL_PREFIX) == false)
            return false;

        RoomProtocol.TYPE proto = RoomProtocol.TYPE.values()[Integer.parseInt(mapData.get(Config.PROTOCOL_PREFIX).toString())];
        switch (proto) {
            case CHAT: {
                roomProtocol.chat(session, mapData.get("chat").toString());
                break;
            }
        }

        return false;
    }

    private void failHandler(final WebSocketSession session, final String tag) throws Exception {
        session.sendMessage(new TextMessage(
                "{" + Config.PROTOCOL_SUC + ":false, tag:" + tag + "}"));

        System.out.println("send to user that a message");
    }
}
