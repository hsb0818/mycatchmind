package com.khwebgame.core.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khwebgame.config.Config;
import com.khwebgame.core.protocol.GameProtocol;
import com.khwebgame.core.protocol.GameProtocolImpl;
import com.khwebgame.core.protocol.LobbyProtocol;
import com.khwebgame.core.protocol.LobbyProtocolImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.*;

public class GameSocketHandler extends TextWebSocketHandler {
    GameProtocol gameProtocol = new GameProtocolImpl();
    List<WebSocketSession> sessions = new ArrayList<>();

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

        String roomName = session.getAttributes().get(Config.SESS_ROOM_NAME).toString();
        UUID roomUid = UUID.fromString(session.getAttributes().get(Config.SESS_ROOM_UID).toString());
        GameRoomMng.getInstance().enter(roomUid, session, roomName);
        System.out.println(session.getId() + " room readyCount has been inited");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        gameProtocol.breakGame(session);
        sessions.remove(session);
        System.out.println(session.getId() + " disconnected");
    }

    private boolean handler(final WebSocketSession session, final Map<String, Object> mapData) throws Exception {
        if (!mapData.containsKey(Config.PROTOCOL_PREFIX))
            return false;

        GameProtocol.TYPE proto = GameProtocol.TYPE.values()[Integer.parseInt(mapData.get(Config.PROTOCOL_PREFIX).toString())];
        switch (proto) {
            case JOIN: {
                gameProtocol.join(session);
                break;
            }
            case GAMESTART: {
                gameProtocol.gameStart(session);
                break;
            }
            case CHAT: {
                gameProtocol.chat(session, mapData.get("chat").toString());
                break;
            }
            case DRAW_UPDATE: {
                gameProtocol.drawUpdate(session,
                        Integer.parseInt(mapData.get("x").toString()),
                        Integer.parseInt(mapData.get("y").toString()),
                        Integer.parseInt(mapData.get("init").toString()));
                break;
            }
            case SELECT_STATE: {
                gameProtocol.selectState(session, Integer.parseInt(mapData.get("state").toString()));
                break;
            }
            case CORANSWER: {
                gameProtocol.correctAnswer(session, mapData.get("word").toString());
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