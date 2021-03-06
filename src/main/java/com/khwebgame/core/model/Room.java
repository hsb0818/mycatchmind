package com.khwebgame.core.model;

import com.khwebgame.config.Config;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.UUID;

public class Room {
    private UUID id = UUID.randomUUID();
    private String name = "";
    private ArrayList<WebSocketSession> users = new ArrayList<>();
    private int readyCount = 0;
    private int order = 0;
    private WebSocketSession drawer = null;
    private String corAnswer = "";

    public void setCorAnswer(String corAnswer) {
        this.corAnswer = corAnswer;
    }

    public String getCorAnswer() {
        return corAnswer;
    }

    public Room(String _name, WebSocketSession user) {
        name = _name;
        users.add(user);
    }

    public Room(String _name, WebSocketSession user, UUID _roomUID) {
        name = _name;
        users.add(user);
        id = _roomUID;
    }

    public UUID getId() { return id; }
    public void setId(UUID _id) { id = _id; }
    public String getName() { return name; }
    public void setName(String _name) { name = _name; }

    public WebSocketSession runOrder() {
        int tempOrder = (order++) % users.size();
        drawer = users.get(tempOrder);
        return drawer;
    }

    public WebSocketSession getDrawer() {
        return drawer;
    }

    public void initReadyCount() { readyCount = 0; }
    public boolean readyToStart() {
        System.out.println("readyCount : " + readyCount);
        return (readyCount > 1) && (readyCount == users.size());
    }

    public boolean incReadyCount() {
        if (this.readyCount >= users.size()) {
            return false;
        }

        this.readyCount++;
        return true;
    }

    public boolean decReadyCount() {
        if (this.readyCount <= 0) {
            return false;
        }

        this.readyCount--;
        return true;
    }

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

    public ArrayList<User> getAnotherUsers(final String id) {
        ArrayList<User> list = new ArrayList<>();
        for (WebSocketSession member : users) {
            if (member.getAttributes().get(Config.SESS_USER_ID).toString().equals(id))
                continue;

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
