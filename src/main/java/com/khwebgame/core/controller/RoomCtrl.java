package com.khwebgame.core.controller;

import com.khwebgame.core.network.RoomMng;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/room", method = { RequestMethod.GET, RequestMethod.POST })
public class RoomCtrl {
    @RequestMapping(value = "/in/{uid}")
    public Map<String, Object> in() {

        Map<String, Object> map = new HashMap();

//        map.put("name", )

        return map;
    }
}
