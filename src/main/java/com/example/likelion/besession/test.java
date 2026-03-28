package com.example.likelion.besession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;

@SpringBootApplication
public class test {

    public static void main(String[] args) {
        SpringApplication.run(com.example.likelionkang.LikeLionKangApplication.class, args);

        System.out.println("conflict occur!");
    }

}
