package com.khwebgame.core.protocol;

import com.khwebgame.core.model.Vector3;
import org.springframework.web.socket.WebSocketSession;

public class GameProtocolImpl implements GameProtocol {
    @Override
    public void gameStart(WebSocketSession session) throws Exception {
        // have to count
    }

    @Override
    public void gameStartEnd(WebSocketSession session) throws Exception {

    }

    @Override
    public void moveUpdateRecv(WebSocketSession session, Vector3 pos) throws Exception {

    }
}
