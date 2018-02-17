package com.khwebgame.core.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import com.khwebgame.core.model.Vector3;
import com.khwebgame.core.model.WordTable;
import com.khwebgame.core.network.DrawUpdatePacket;
import com.khwebgame.core.network.GameRoomMng;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameProtocolImpl implements GameProtocol {
    @Override
    public void join(WebSocketSession session) throws Exception {
        Room room = GameRoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;

        room.incReadyCount();
        boolean readyToStart = room.readyToStart();

        ArrayList<Map<String, Object>> users = new ArrayList<>();
        for (final WebSocketSession user : room.getUserSessions()) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getAttributes().get(Config.SESS_USER_ID));
            map.put("userName", user.getAttributes().get(Config.SESS_USER_NAME));
            users.add(map);
        }

        for (final WebSocketSession user : room.getUserSessions()) {
            Map<String, Object> map = new HashMap<>();
            map.put(Config.PROTOCOL_PREFIX, TYPE.JOIN.getVal());
            map.put(Config.PROTOCOL_SUC, true);
            map.put("users", users);

            if (user.getId().equals(session.getId()))
                map.put("readyToStart", readyToStart ? 1 : 0);
            else
                map.put("readyToStart", 0);

            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }
    }

    @Override
    public void gameStart(WebSocketSession session) throws Exception {
        Room room = GameRoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;

        boolean readyToStart = room.readyToStart();
        if (!readyToStart) {
            System.out.println("has been received gameStart protocol but readyToStart value is not enough.");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.GAMESTART.getVal());
        map.put(Config.PROTOCOL_SUC, true);

        WebSocketSession drawer = room.runOrder();
        map.put("drawerID", drawer.getAttributes().get(Config.SESS_USER_ID));

        room.setCorAnswer(WordTable.getInstance().randomWord());
        for (final WebSocketSession user : room.getUserSessions()) {
            if (user == drawer) {
                map.put("word", room.getCorAnswer());
            }
            else {
                map.put("word", "?");
            }

            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }
    }

    @Override
    public void chat(WebSocketSession session, String chat) throws Exception {
        Room room = GameRoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, GameProtocol.TYPE.CHAT.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("userName", session.getAttributes().get(Config.SESS_USER_NAME));
        map.put("chat", chat);

        for (final WebSocketSession user : room.getUserSessions()) {
            if (user.getId().equals(session.getId()))
                continue;

            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }
    }

    @Override
    public void drawUpdate(WebSocketSession session, int x, int y, int init) throws Exception {
        Room room = GameRoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;
        else if (!room.getDrawer().getId().equals(session.getId()))
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.DRAW_UPDATE.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("x", x);
        map.put("y", y);
        map.put("init", init);

        for (final WebSocketSession user : room.getUserSessions()) {
            if (user.getId().equals(session.getId()))
                continue;

            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }
    }

    @Override
    public void selectState(WebSocketSession session, int state) throws Exception {
        Room room = GameRoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;
        else if (!room.getDrawer().getId().equals(session.getId()))
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.SELECT_STATE.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("state", state);

        for (final WebSocketSession user : room.getUserSessions()) {
            if (user.getId().equals(session.getId()))
                continue;

            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }
    }

    @Override
    public void correctAnswer(WebSocketSession session, String correctAnswer) throws Exception {
        Room room = GameRoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.CORANSWER.getVal());
        map.put(Config.PROTOCOL_SUC, true);
        map.put("userName", session.getAttributes().get(Config.SESS_USER_NAME));

        boolean correct = room.getCorAnswer().equals(correctAnswer);
        map.put("correct", correct ? 1: 0);

        if (correct) {
            WebSocketSession drawer = room.runOrder();
            map.put("drawerID", drawer.getAttributes().get(Config.SESS_USER_ID));

            room.setCorAnswer(WordTable.getInstance().randomWord());
            for (final WebSocketSession user : room.getUserSessions()) {
                if (user == drawer) {
                    map.put("word", room.getCorAnswer());
                } else {
                    map.put("word", "?");
                }

                user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
            }
        }
        else {
            for (final WebSocketSession user : room.getUserSessions()) {
                user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
            }
        }
    }

    @Override
    public void breakGame(WebSocketSession session) throws Exception {
        Room room = GameRoomMng.getInstance().getRoom(UUID.fromString(session.getAttributes()
                .get(Config.SESS_ROOM_UID).toString()));

        if (room == null)
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Config.PROTOCOL_PREFIX, TYPE.BREAK_GAME.getVal());
        map.put(Config.PROTOCOL_SUC, true);

        for (final WebSocketSession user : room.getUserSessions()) {
            if (user.getId().equals(session.getId()))
                continue;

            user.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
        }

        //delete room
        GameRoomMng.getInstance().removeById(room.getId());
    }
}
