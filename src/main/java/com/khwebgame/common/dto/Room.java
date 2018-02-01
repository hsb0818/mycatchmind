package com.khwebgame.common.dto;

import com.khwebgame.config.Config;

import java.util.UUID;

public class Room {
    private UUID id = UUID.randomUUID();
    private int count = 1;
    private String name = "";

    public Room(String _name) {
        name = _name;
    }

    public void incCount() {
        count += (count < Config.MAX_ROOM_USERS) ? 1 : 0;
    }
    public void decCount() {
        count += (count > 0) ? -1 : 0;
    }

    public UUID getId() { return id; }
    public void setId(UUID _id) { id = _id; }
    public int getCount() { return count; }
    public void setCount(int _count) { count = _count; }
    public String getName() { return name; }
    public void setName(String _name) { name = _name; }
}
