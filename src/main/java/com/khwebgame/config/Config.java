package com.khwebgame.config;

import com.khwebgame.core.model.WordTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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

    public static byte[] objToByte(Object obj) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
        objStream.writeObject(obj);

        return byteStream.toByteArray();
    }

    public static Object byteToObj(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objStream = new ObjectInputStream(byteStream);

        return objStream.readObject();
    }
}