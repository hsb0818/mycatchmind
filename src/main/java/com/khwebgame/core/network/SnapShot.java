package com.khwebgame.core.network;

import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.UUID;

public class SnapShot {
    public enum TYPE {
        NONE(0),
        GAME(1),
        MOVE(2),
        BATTLE(3);

        private int val;
        TYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }

    public TYPE type = TYPE.NONE;
    public WebSocketSession user = null;
    public long serverTime = 0;
    public long deltaTime = 0;
    public Room room = null;

    public SnapShot(WebSocketSession _user, long _serverTime, long _delTime) {
        user = _user;
        serverTime = _serverTime;
        deltaTime = _delTime;

        room = RoomMng.getInstance().getRoom(UUID
                .fromString(user.getAttributes().get(Config.SESS_ROOM_UID).toString()));

        assert (room != null) : "[SnapShot][Constructor] room is null";
    }
}
