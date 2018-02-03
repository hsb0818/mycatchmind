package com.khwebgame.core.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.khwebgame.core.model.Room;
import com.khwebgame.core.network.RoomMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.HashMap;

@RestController
public class LobbyProtocol {
    @Autowired
    @Qualifier("GsonMod")
    Gson gson;

    @MessageMapping("/lobby/createroom")
    @SendTo("/khcli-lobby/createroom")
    public String createRoom(String message) throws  Exception {
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> map = gson.fromJson(message, type);

        RoomMng.getInstance().insert(new Room(map.get("name")));

        System.out.println(map.get("name"));
        return message;
    }

    @MessageMapping("/lobby/roomlist") // khwebgame/hello
    @SendTo("/khcli-lobby/roomlist")
    public String roomList() throws Exception {

        if (RoomMng.getInstance().getCount() == 0)
            return null;

        return gson.toJson(RoomMng.getInstance().getmRooms());
    }
}