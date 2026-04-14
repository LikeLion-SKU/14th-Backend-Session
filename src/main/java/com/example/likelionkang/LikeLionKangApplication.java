package com.example.likelionkang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
    public class LikeLionKangApplication {

        public static void main(String[] args) {
            SpringApplication.run(LikeLionKangApplication.class, args);
        }

    }
