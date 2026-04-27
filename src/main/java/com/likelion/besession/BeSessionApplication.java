package com.likelion.besession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing    //Jpa 생성일 자동으로 기록하도록 Jpa Auditing 기능 활성화
public class BeSessionApplication {

  public static void main(String[] args) {
    SpringApplication.run(BeSessionApplication.class, args);
  }

}
