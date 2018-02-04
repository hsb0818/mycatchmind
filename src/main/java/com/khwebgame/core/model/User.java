package com.khwebgame.core.model;

public class User {
    String id;
    String name;

    public User(String _id, String _name) {
        id = _id;
        name = _name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
