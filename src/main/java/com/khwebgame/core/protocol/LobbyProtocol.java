package com.khwebgame.core.protocol;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;

public interface LobbyProtocol {
    public enum TYPE {
        ROOM_JOIN(0),
        ROOM_CREATE(1),
        ROOMINFO_UPDATE(2);

        private int val;
        TYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }

    public void roomJoin(WebSocketSession session, UUID roomUid) throws Exception;
    public void roomCreate(WebSocketSession session, String name) throws Exception;
    public void roomInfoUpdateResponse(WebSocketSession session) throws Exception;
}
