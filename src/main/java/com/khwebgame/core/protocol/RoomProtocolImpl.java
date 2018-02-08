package com.khwebgame.core.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import com.khwebgame.core.model.User;
import com.khwebgame.core.network.RoomMng;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoomProtocolImpl implements RoomProtocol {
    @Override
    public void join(WebSocketSession session) throws Exception {
        Room room = RoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.JOIN.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("joinUserID", session.getAttributes().get(Config.SESS_USER_ID));
        map.put("joinUserName", session.getAttributes().get(Config.SESS_USER_NAME));

        for (final WebSocketSession user : room.getUserSessions()) {
            if (user.getId().equals(session.getId()))
                continue;

            System.out.println(user.getAttributes().get(Config.SESS_USER_ID));
            System.out.println(session.getAttributes().get(Config.SESS_USER_ID));
            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }
    }

    @Override
    public void chat(WebSocketSession session, String chat) throws Exception {
        Room room = RoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.CHAT.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("chat", chat);

        for (final WebSocketSession user : room.getUserSessions()) {
            if (user.getId().equals(session.getId()))
                continue;

            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }
    }

    @Override
    public void ready(WebSocketSession session, boolean display) throws Exception {
        Room room = RoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.READY.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("userId", session.getAttributes().get(Config.SESS_USER_ID).toString());
        map.put("display", display ? 1 : 0);

        System.out.println("asdasdsadasds");

        for (final WebSocketSession user : room.getUserSessions()) {
            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }
    }
}
