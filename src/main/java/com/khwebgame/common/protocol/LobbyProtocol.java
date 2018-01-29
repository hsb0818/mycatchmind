package com.khwebgame.common.protocol;

import com.khwebgame.common.model.Room;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LobbyProtocol {
    @MessageMapping("/createroom")
    @SendTo("/khcli-lobby/createroom")
    public String createRoom(String message) throws  Exception {
        System.out.println(message);
        return message;
    }

    @MessageMapping("/roomlist") // khwebgame/hello
    @SendTo("/khcli-lobby/roomlist")
    public List<Room> roomList(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return null;
    }
}