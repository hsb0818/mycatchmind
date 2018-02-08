package com.khwebgame.core.controller;

import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import com.khwebgame.core.network.RoomMng;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping(value = "/game", method = { RequestMethod.GET, RequestMethod.POST })
public class GameCtrl {
    @RequestMapping(value = "/in/{roomName}")
    public String game(final HttpSession session, Model model) {
        UUID roomUid = UUID.fromString(session.getAttribute(Config.SESS_ROOM_UID).toString());
        Room room = RoomMng.getInstance().getRoom(roomUid);

        model.addAttribute("roomName", room.getName());

        return "game";
    }
}
