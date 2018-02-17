package com.khwebgame.core.controller;

import com.khwebgame.config.Config;
import com.khwebgame.core.model.Room;
import com.khwebgame.core.model.WordTable;
import com.khwebgame.core.network.RoomMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Controller
@RequestMapping(value = "/game", method = { RequestMethod.GET, RequestMethod.POST })
public class GameCtrl {
    @RequestMapping(value = "/in/{roomName}")
    public String game(final HttpSession session, Model model) {
        model.addAttribute("roomName", session.getAttribute(Config.SESS_ROOM_NAME));
        model.addAttribute("myId", session.getAttribute(Config.SESS_USER_ID));
        model.addAttribute("myName", session.getAttribute(Config.SESS_USER_NAME));

        return "game";
    }
}
