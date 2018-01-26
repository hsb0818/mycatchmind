package com.khwebgame.core.controller;

import com.khwebgame.core.bo.TestService;
import com.khwebgame.core.model.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserCtrl {

    @Autowired
    TestService testService;

    @RequestMapping(value = "tdb")
    public @ResponseBody LoginInfo tdb() throws Exception {
        return testService.selectTest();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("id") String id, @RequestParam("pw") String pw) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
}
