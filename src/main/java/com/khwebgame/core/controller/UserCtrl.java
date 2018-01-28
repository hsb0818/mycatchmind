package com.khwebgame.core.controller;

import com.khwebgame.config.Config;
import com.khwebgame.core.bo.LoginBO;
import com.khwebgame.core.model.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Request;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user", method = { RequestMethod.GET, RequestMethod.POST })
public class UserCtrl {

    @Autowired
    LoginBO loginBO;

    @RequestMapping(value = "/login_page")
    public String loginPage() {
        return "login_page";
    }

    @RequestMapping(value = "/lobby")
    public String test(HttpSession session, Model model) {
        model.addAttribute("id", session.getAttribute(Config.SESS_USER_KEY));
        return "lobbyroom";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(final HttpSession session, Model model,
                        @RequestParam("id")String id, @RequestParam("pw")String pw) {

        LoginInfo loginInfo = loginBO.Verification(id, pw);
        if (loginInfo == null)
            return "login_page";

        System.out.println("user login");
        session.setAttribute(Config.SESS_USER_KEY, id);
        return "redirect:lobby";
    }
}
