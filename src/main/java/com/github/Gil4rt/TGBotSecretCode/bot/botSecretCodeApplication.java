package com.github.Gil4rt.TGBotSecretCode.bot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

import java.io.IOException;

@SpringBootApplication
public class botSecretCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(botSecretCodeApplication.class, args);

        database db = new database();

        try {
            db.callDatabase();
            db.updateDatabase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
