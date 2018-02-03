package com.khwebgame.core.network;

import com.khwebgame.core.model.User;

import java.util.ArrayList;
import java.util.Queue;

public class UserMng {
    private UserMng() {}
    private UserMng(UserMng ins) {}

    public static synchronized UserMng getInstance() {
        if (instance == null) {
            instance = new UserMng();
        }

        return instance;
    }

    public void add(String id, String username) {
        users.add(new User(id, username));
    }

    public void remove(String username) {

    }

    private static UserMng instance;
    private Queue<User> users;
}
