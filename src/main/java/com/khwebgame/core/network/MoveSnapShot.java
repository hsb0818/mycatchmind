package com.khwebgame.core.network;

import com.khwebgame.core.model.Room;
import com.khwebgame.core.model.Vector3;
import org.springframework.web.socket.WebSocketSession;

public class MoveSnapShot extends SnapShot {
    public Vector3 pos;
    public float angle = 0f;

    public MoveSnapShot(WebSocketSession user, long _serverTime, long _delTime
    , Vector3 _pos, float _angle) {
        super(user, _serverTime, _delTime);

        type = TYPE.MOVE;
        pos = _pos;
        angle = _angle;
    }
}
