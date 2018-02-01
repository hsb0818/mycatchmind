package com.khwebgame.common.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.khwebgame.common.dto.Room;
import com.khwebgame.core.network.RoomMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LobbyProtocol {
    @Autowired
    @Qualifier("GsonMod")
    Gson gson;

    @MessageMapping("/createroom")
    @SendTo("/khcli-lobby/createroom")
    public String createRoom(String message) throws  Exception {
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> map = gson.fromJson(message, type);

        RoomMng.getInstance().insert(new Room(map.get("name")));

        System.out.println(map.get("name"));
        return message;
    }

    @MessageMapping("/roomlist") // khwebgame/hello
    @SendTo("/khcli-lobby/roomlist")
    public String roomList() throws Exception {

        if (RoomMng.getInstance().getCount() == 0)
            return null;

        return gson.toJson(RoomMng.getInstance().getmRooms());
    }
}