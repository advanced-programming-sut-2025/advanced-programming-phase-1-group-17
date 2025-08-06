package io.github.StardewValley.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StardewServer {
    public static void main(String[] args) {
        SpringApplication.run(StardewServer.class, args);
    }
}
