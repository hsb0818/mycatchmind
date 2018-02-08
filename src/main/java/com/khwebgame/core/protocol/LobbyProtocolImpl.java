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

public class LobbyProtocolImpl implements LobbyProtocol {
    @Override
    public void roomJoin(WebSocketSession session, UUID roomUid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.ROOM_JOIN.getVal());

        Room room = RoomMng.getInstance().enter(roomUid, session);
        if (room == null) {
            map.put(Config.PROTOCOL_SUC, false);
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
            System.out.println("[roomJoin] room is null. check room uuid or user");
            return;
        }

        map.put(Config.PROTOCOL_SUC, true);
        map.put("roomName", room.getName());
        map.put(Config.SESS_ROOM_UID, room.getId());

        session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));

        System.out.println(session.getAttributes().get(Config.SESS_USER_NAME) + " entered room : " + roomUid.toString());
    }

    @Override
    public void roomCreate(WebSocketSession session, String name) throws Exception {
        Room room = RoomMng.getInstance().insert(new Room(name, session));

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.ROOM_CREATE.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("roomName", room.getName());
        map.put(Config.SESS_ROOM_UID, room.getId());
        map.put("users", room.getUsers());
        session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));

        System.out.println(room.getId() + "has been created a room : " + name);
    }

    @Override
    public void roomInfoUpdateResponse(WebSocketSession session) throws Exception {
        ArrayList<Map<String, Object>> rooms = new ArrayList<>();
        for (final Map.Entry<UUID, Room> room : RoomMng.getInstance().getRooms().entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put(Config.SESS_ROOM_UID, room.getValue().getId());
            map.put("roomName", room.getValue().getName());
            map.put("roomCount", room.getValue().userCount());
            rooms.add(map);
        }

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.ROOMINFO_UPDATE.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("rooms", rooms);

        session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        System.out.println("room update message to " + session.getAttributes().get(Config.SESS_USER_NAME));
    }
}
