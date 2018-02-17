package com.khwebgame.core.network;

import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LobbyRoomMng {
    private LobbyRoomMng() {}
    private LobbyRoomMng(RoomMng ins) {}

    public static synchronized LobbyRoomMng getInstance() {
        if (instance == null) {
            instance = new LobbyRoomMng();
        }

        return instance;
    }

    private static LobbyRoomMng instance;

    public Map<UUID, Room> getRooms() {
        return mRooms;
    }

    public boolean removeById(UUID roomUid) {
        synchronized (LobbyRoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return false;

            final Room room = mRooms.get(roomUid);
            mRooms.remove(roomUid);
            return true;

        }
    }

    public int getCount() { return mRooms.size(); }

    public Room insert(Room room) {
        synchronized (RoomMng.class) {
            mRooms.put(room.getId(), room);
            return room;
        }
    }

    public Room enter(final UUID roomUid, final WebSocketSession user) {
        synchronized (LobbyRoomMng.class) {
            Room room = null;

            if (!mRooms.containsKey(roomUid)) {
                room = new Room(roomUid.toString(), user, roomUid);
                mRooms.put(room.getId(), room);
                return room;
            }

            room = mRooms.get(roomUid);
            if (room.isIn(user))
                return null;

            room.enter(user);
            return room;
        }
    }

    public Room leave(final UUID roomUid, final WebSocketSession user) {
        synchronized (LobbyRoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return null;

            final Room room = mRooms.get(roomUid);
            if (!room.isIn(user))
                return null;

            room.leave(user);
            return room;
        }
    }

    public boolean initReadyCount(UUID roomUid) {
        synchronized (LobbyRoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return false;

            Room room = mRooms.get(roomUid);
            room.initReadyCount();
            return true;
        }
    }

    public Room getRoom(final UUID roomUid) {
        synchronized (LobbyRoomMng.class) {
            if (!mRooms.containsKey(roomUid))
                return null;

            return mRooms.get(roomUid);
        }
    }

    private Map<UUID, Room> mRooms = new HashMap<>();
}
