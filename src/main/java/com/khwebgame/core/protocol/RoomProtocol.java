package com.khwebgame.core.protocol;

import org.springframework.web.socket.WebSocketSession;

public interface RoomProtocol {
    public enum TYPE {
        CHAT(0);

        private int val;
        TYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }

    public void chat(WebSocketSession session, String chat) throws Exception;
}
