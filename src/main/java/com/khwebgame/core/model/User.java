package com.khwebgame.core.model;

public class User {
    private String nickname;
    private String id;

    public User(String _id, String _nickname) {
        id = _id;
        nickname = _nickname;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
