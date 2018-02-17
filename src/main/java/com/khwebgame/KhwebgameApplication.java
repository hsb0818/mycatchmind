package com.khwebgame;

import com.khwebgame.core.model.WordTable;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:application.properties")
public class KhwebgameApplication {
	public static void main(String[] args) throws Exception {
        Resource res = new ClassPathResource("static/words.txt");
        WordTable.getInstance().setWords(new ArrayList<>(Files.readAllLines(Paths.get(res.getURI()))));

		SpringApplication.run(KhwebgameApplication.class, args);
	}
}
