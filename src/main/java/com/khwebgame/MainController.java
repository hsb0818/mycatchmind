package com.khwebgame;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("contents", "demo contents");
        return "index";
    }

    @RequestMapping(value = "/menu", method= RequestMethod.GET)
    public String menu(Model model) {
        model.addAttribute("contents", "demo contents");
        return "menu";
    }

    @RequestMapping(value = "/game", method= RequestMethod.GET)
    public String game(Model model) {
        return "game";
    }

    @RequestMapping(value = "/lobbyroom", method= RequestMethod.GET)
    public String lobbyroom(Model model) {
        return "lobbyroom";
    }
}
