package com.khwebgame.core.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import com.khwebgame.core.network.LobbyRoomMng;
import com.khwebgame.core.network.RoomMng;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LobbyProtocolImpl implements LobbyProtocol {
    @Override
    public void roomCreate(WebSocketSession session, String name) throws Exception {
        UUID roomId = UUID.randomUUID(); // create new room.

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.ROOM_CREATE.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put(Config.SESS_ROOM_NAME, name);
        map.put(Config.SESS_ROOM_UID, roomId);
        session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));

        System.out.println(roomId + ":: has been created as a room " + name);
    }

    @Override
    public void roomInfoUpdateResponse(WebSocketSession session) throws Exception {
        ArrayList<Map<String, Object>> rooms = new ArrayList<>();
        for (final Map.Entry<UUID, Room> room : RoomMng.getInstance().getRooms().entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put(Config.SESS_ROOM_UID, room.getValue().getId());
            map.put(Config.SESS_ROOM_NAME, room.getValue().getName());
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
