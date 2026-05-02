package com.likjelion.besession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //엔티티의 생성일, 수정일을 자동으로 기록하도록 JpaAuditing 기능 활성하
public class BeSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeSessionApplication.class, args);
    }

}
