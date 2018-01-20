package com.khwebgame;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public String demo(Model model) {

        model.addAttribute("contents", "demo contents");
        return "index";
    }
}
