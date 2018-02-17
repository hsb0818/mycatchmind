package com.khwebgame.core.network;

import com.khwebgame.core.model.Vector2;

public class DrawUpdatePacket {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
