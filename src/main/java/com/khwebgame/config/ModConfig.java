package com.khwebgame.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModConfig {
    @Bean(name = "GsonMod")
    public Gson getGson() {
        return new Gson();
    }
}
