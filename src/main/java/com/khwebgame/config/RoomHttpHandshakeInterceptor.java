package com.khwebgame.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class RoomHttpHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession();
            attributes.put(Config.SESS_USER_ID, session.getAttribute(Config.SESS_USER_ID));
            attributes.put(Config.SESS_USER_NAME, session.getAttribute(Config.SESS_USER_NAME));
            attributes.put(Config.SESS_ROOM_UID, session.getAttribute(Config.SESS_ROOM_UID));
            attributes.put(Config.SESS_ROOM_NAME, session.getAttribute(Config.SESS_ROOM_NAME));
            attributes.put("sessionId", session.getId());

            System.out.println("Entered in [Room] websocket handshake intercepter : " + session.getId().toString());
            System.out.println("user id : " + session.getAttribute(Config.SESS_USER_ID));
            System.out.println("user name : " + session.getAttribute(Config.SESS_USER_NAME));
            System.out.println("user room UID : " + session.getAttribute(Config.SESS_ROOM_UID));
            System.out.println("user room Name : " + session.getAttribute(Config.SESS_ROOM_NAME));
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
    }
}