package com.khwebgame.core.network;

import com.khwebgame.common.dto.Room;
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

    public ArrayList<Room> getmRooms() {
        return mRooms;
    }

    public void insert(Room room) {
        synchronized (RoomMng.class) {
            mRooms.add(room);
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

    private ArrayList<Room> mRooms = new ArrayList<Room>();
}
