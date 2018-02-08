package com.khwebgame.core.model;

public class Vector3 {
    public float x = 0f;
    public float y = 0f;
    public float z = 0f;

    public Vector3() {}
    public Vector3(final float _x, final float _y) {
        x = _x;
        y = _y;
    }
    public Vector3(final float _x, final float _y, final float _z) {
        x = _x;
        y = _y;
        z = _z;
    }
}
