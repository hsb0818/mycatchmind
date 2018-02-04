package com.khwebgame.core.network;

import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
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

    public ArrayList<Room> getRooms() {
        return mRooms;
    }

    public Room insert(Room room) {
        synchronized (RoomMng.class) {
            mRooms.add(room);
            return room;
        }
    }

    public void removeById(UUID id) {
        synchronized (RoomMng.class) {
            for (int i = 0; i < mRooms.size(); i++) {
                if (mRooms.get(i).getId() != id)
                    continue;

                mRooms.remove(i);
                return;
            }
        }
    }

    public void removeByIdx(int idx) {
        synchronized (RoomMng.class) {
            mRooms.remove(idx);
        }
    }

    public int getCount() { return mRooms.size(); }

    public Room enter(final UUID roomUid, final WebSocketSession user) {
        synchronized (RoomMng.class) {
            for (final Room room : mRooms) {
                if (room.getId() != roomUid)
                    continue;
                if (room.isIn(user) == false)
                    continue;

                room.enter(user);
                return room;
            }

            return null;
        }
    }

    public Room leave(final WebSocketSession user) {
        synchronized (RoomMng.class) {
            for (final Room room : mRooms) {
                room.leave(user);
                return room;
            }

            return null;
        }
    }

    public boolean updateWebSocketSession(UUID roomUid, WebSocketSession sameUser, String sessonID) {
        synchronized (RoomMng.class) {
            for (final Room room : mRooms) {
                System.out.println("---------------");
                System.out.println(room.getId());
                System.out.println(roomUid);
                System.out.println("111111111111111");
                if (room.getId().compareTo(roomUid) != 0)
                    continue;

                ArrayList<WebSocketSession> users = room.getUserSessions();
                System.out.println("user count : " + room.userCount());
                for (final WebSocketSession user : users) {
                    System.out.println(user.getId());
                    if (user.getAttributes().get(Config.SESS_USER_ID).toString().equals(sessonID) == false)
                        continue;

                    // replace old session to new session on same user.
                    users.remove(user);
                    users.add(sameUser);

                    System.out.println(sameUser.getId());
                    System.out.println("founded and replaced!");
                    return true;
                }

                break;
            }

            return false;
        }
    }

    private ArrayList<Room> mRooms = new ArrayList<Room>();
}
