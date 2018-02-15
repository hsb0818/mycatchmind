package com.khwebgame.core.controller;

import com.khwebgame.config.Config;
import com.khwebgame.core.bo.LoginBO;
import com.khwebgame.core.dto.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserCtrl {
    static final String context = "/user";

    @Autowired
    LoginBO loginBO;

    @RequestMapping(value = context + "/login_page")
    public String loginPage(HttpSession session, Model model) {
        Object isLogined = session.getAttribute(Config.SESS_USER_NAME);
        if (isLogined != null)
            return "redirect:lobby";

        return "login_page";
    }

    @RequestMapping(value = context + "/lobby")
    public String lobby(HttpSession session, Model model) {
        model.addAttribute("id", session.getAttribute(Config.SESS_USER_NAME));
        return "lobby";
    }

    @RequestMapping(value = context + "/login", method = RequestMethod.POST)
    public String login(final HttpSession session, Model model,
                        @RequestParam("id")String id, @RequestParam("pw")String pw) {

        LoginInfo loginInfo = loginBO.Verification(id, pw);
        if (loginInfo == null)
            return "login_page";

        System.out.println("user login");
        session.setAttribute(Config.SESS_USER_ID, loginInfo.getId());
        session.setAttribute(Config.SESS_USER_NAME, loginInfo.getNickname());

        return "redirect:lobby";
    }
}
