package com.khwebgame.core.protocol;

import com.google.gson.Gson;
import com.khwebgame.config.Config;
import com.khwebgame.core.network.UserMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserProtocol {
    @Autowired
    @Qualifier("GsonMod")
    Gson gson;

    @MessageMapping("/user/connection")
    public void connection(SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();

//        System.out.println(headerAccessor.getSessionAttributes().get(Config.SESS_USER_KEY).toString());
//        UserMng.getInstance().add();
    }
}
