package com.khwebgame.core.network;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.khwebgame.common.model.Room;

import java.util.ArrayList;

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

    public void insert(Room room) {
        mRooms.add(room);
    }

    public void removeById(int id) {
        for (int i=0; i<mRooms.size(); i++) {
            if (mRooms.get(i).getId() != id)
                continue;

            mRooms.remove(i);
            return;
        }
    }

    public void removeByIdx(int idx) {
        mRooms.remove(idx);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(mRooms);
    }

    private ArrayList<Room> mRooms = new ArrayList<Room>();
}
