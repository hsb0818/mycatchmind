package com.khwebgame.core.model;

import com.khwebgame.config.Config;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.UUID;

public class Room {
    private UUID id = UUID.randomUUID();
    private String name = "";
    private ArrayList<WebSocketSession> users = new ArrayList<>();

    public Room(String _name, WebSocketSession user) {
        name = _name;
        users.add(user);
    }

    public UUID getId() { return id; }
    public void setId(UUID _id) { id = _id; }
    public String getName() { return name; }
    public void setName(String _name) { name = _name; }

    public ArrayList<WebSocketSession> getUserSessions() {
        return users;
    }
    public ArrayList<User> getUsers() {
        ArrayList<User> list = new ArrayList<>();
        for (WebSocketSession member : users) {
            list.add(new User(member.getAttributes().get(Config.SESS_USER_ID).toString(),
                    member.getAttributes().get(Config.SESS_USER_NAME).toString())
            );
        }

        return list;
    }

    public void enter(final WebSocketSession user) {
        users.add(user);
    }

    public void leave(final WebSocketSession user) {
        users.remove(user);
    }

    public int userCount() {
        return users.size();
    }

    public boolean isIn(final WebSocketSession user) {
        for (WebSocketSession member : users) {
            if (member.getId() != user.getId())
                continue;

            return true;
        }

        return false;
    }
}
