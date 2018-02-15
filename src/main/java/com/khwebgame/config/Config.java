package com.khwebgame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public final static String SESS_USER_ID = "USER_ID";
    public final static String SESS_USER_NAME = "USER_NAME";
    public final static String SESS_ROOM_UID = "USER_ROOM_UID";
    public final static String SESS_ROOM_NAME = "USER_ROOM_NAME";
    public final static int MAX_ROOM_USERS = 4;
    public final static String PROTOCOL_PREFIX = "proto";
    public final static String PROTOCOL_SUC = "suc";
    public final static String SERVER_IP = "14.38.170.232";
}