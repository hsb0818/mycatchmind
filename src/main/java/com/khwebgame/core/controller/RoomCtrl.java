package com.khwebgame.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import com.khwebgame.core.network.LobbyRoomMng;
import com.khwebgame.core.network.RoomMng;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/room", method = { RequestMethod.GET, RequestMethod.POST })
public class RoomCtrl {
    @RequestMapping(value = "/session")
    @ResponseBody
    public boolean session(final HttpSession session,
                           @RequestParam(value = Config.SESS_ROOM_UID)String uid,
                           @RequestParam(value = Config.SESS_ROOM_NAME)String roomName) {
        session.setAttribute(Config.SESS_ROOM_UID, uid);
        session.setAttribute(Config.SESS_ROOM_NAME, roomName);
        System.out.println(uid);
        System.out.println(roomName);

        return true;
    }

    @RequestMapping(value = "/in/{roomName}")
    public String in(final HttpSession session, Model model,
                     @PathVariable String roomName) {

        model.addAttribute("roomName", roomName);
        model.addAttribute("myId", session.getAttribute(Config.SESS_USER_ID));
        model.addAttribute("myName", session.getAttribute(Config.SESS_USER_NAME));

        return "room";
    }
}
