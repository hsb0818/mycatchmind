package com.khwebgame.core.protocol;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;

public interface LobbyProtocol {
    public enum TYPE {
        ROOM_CREATE(0),
        ROOMINFO_UPDATE(1);

        private int val;
        TYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }

    public void roomCreate(WebSocketSession session, String name) throws Exception;
    public void roomInfoUpdateResponse(WebSocketSession session) throws Exception;
}
