package com.khwebgame.core.protocol;

import org.springframework.web.socket.WebSocketSession;

public interface RoomProtocol {
    public enum TYPE {
        JOIN(0),
        CHAT(1),
        READY(2),
        USER_EXIT(3);

        private int val;
        TYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }

    public void join(WebSocketSession session) throws Exception;
    public void chat(WebSocketSession session, String chat) throws Exception;
    public void ready(WebSocketSession session, boolean display) throws Exception;
    public void userExit(WebSocketSession session) throws Exception;
}
