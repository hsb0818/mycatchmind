package com.khwebgame.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khwebgame.config.Config;
import com.khwebgame.core.network.RoomMng;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/room", method = { RequestMethod.GET, RequestMethod.POST })
public class RoomCtrl {
    @RequestMapping(value = "/session")
    @ResponseBody
    public boolean session(final HttpSession session, @RequestParam(value = Config.SESS_ROOM_UID)String uid) {
        session.setAttribute(Config.SESS_ROOM_UID, uid);
        System.out.println(uid);
        return true;
    }

    @RequestMapping(value = "/in/{roomName}")
    public String in(final HttpSession session, Model model,
                     @PathVariable String roomName) {



        model.addAttribute("roomName", roomName);
        return "room";
    }
}
