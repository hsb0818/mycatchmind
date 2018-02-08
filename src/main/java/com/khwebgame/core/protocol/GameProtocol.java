package com.khwebgame.core.protocol;

import com.khwebgame.core.model.Vector3;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public interface GameProtocol {
    public enum TYPE {
        GAMESTART(0),
        POSITION_UPDATE(1);

        private int val;
        TYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }

    public void gameStart(WebSocketSession session) throws Exception;
    public void gameStartEnd(WebSocketSession session) throws Exception;
    public void moveUpdateRecv(WebSocketSession session, Vector3 pos) throws Exception;
}
