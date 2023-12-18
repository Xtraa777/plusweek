package com.sparta.plusweek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PlusweekApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlusweekApplication.class, args);
    }

}
