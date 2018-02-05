package com.khwebgame.core.network;

import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoomMng {
    private RoomMng() {}
    private RoomMng(RoomMng ins) {}

    public static synchronized RoomMng getInstance() {
        if (instance == null) {
            instance = new RoomMng();
        }

        return instance;
    }

    private static RoomMng instance;

    public Map<UUID, Room> getRooms() {
        return mRooms;
    }

    public Room insert(Room room) {
        synchronized (RoomMng.class) {
            mRooms.put(room.getId(), room);
            return room;
        }
    }

    public boolean removeById(UUID roomUid) {
        synchronized (RoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return false;

            final Room room = mRooms.get(roomUid);
            mRooms.remove(roomUid);
            return true;

        }
    }

    public int getCount() { return mRooms.size(); }

    public Room enter(final UUID roomUid, final WebSocketSession user) {
        synchronized (RoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return null;

            final Room room = mRooms.get(roomUid);
            if (room.isIn(user))
                return null;

            room.enter(user);
            return room;
        }
    }

    public Room leave(final UUID roomUid, final WebSocketSession user) {
        synchronized (RoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return null;

            final Room room = mRooms.get(roomUid);
            if (!room.isIn(user))
                return null;

            room.leave(user);
            return room;
        }
    }

    public boolean updateWebSocketSession(UUID roomUid, WebSocketSession sameUser, String sessonID) {
        synchronized (RoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return false;

            Room room = mRooms.get(roomUid);
            ArrayList<WebSocketSession> users = room.getUserSessions();
            System.out.println("user count : " + room.userCount());
            for (final WebSocketSession user : users) {
                System.out.println(user.getId());
                if (!user.getAttributes().get(Config.SESS_USER_ID).toString().equals(sessonID))
                    continue;

                // replace old session to new session on same user.
                users.remove(user);
                users.add(sameUser);

                System.out.println(sameUser.getId());
                System.out.println("founded and replaced!");
                return true;
            }

            return false;
        }
    }

    public Room getRoom(final UUID roomUid) {
        synchronized (RoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return null;

            return mRooms.get(roomUid);
        }
    }

    private Map<UUID, Room> mRooms = new HashMap<>();
}
