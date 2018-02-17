package com.khwebgame.core.protocol;

import com.khwebgame.core.model.Vector3;
import com.khwebgame.core.network.DrawUpdatePacket;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public interface GameProtocol {
    public enum TYPE {
        JOIN(0),
        GAMESTART(1),
        CHAT(2),
        DRAW_UPDATE(3),
        SELECT_STATE(4),
        CORANSWER(5),
        BREAK_GAME(6);

        private int val;
        TYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }

    public enum STATETYPE {
        CLEAR(0);

        private int val;
        STATETYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }

    public void join(WebSocketSession session) throws Exception;
    public void gameStart(WebSocketSession session) throws Exception;
    public void chat(WebSocketSession session, String chat) throws Exception;
    public void drawUpdate(WebSocketSession session, int x, int y, int init) throws Exception;
    public void selectState(WebSocketSession session, int state) throws Exception;
    public void correctAnswer(WebSocketSession session, String correctAnswer) throws Exception;
    public void breakGame(WebSocketSession session) throws Exception;
}
