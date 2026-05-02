package com.example.besession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// 엔티티의 생성일, 수정일을 자동으로 기록하도록 JPA Auditing 기능을 활성화!!
@EnableJpaAuditing
public class BeSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeSessionApplication.class, args);
    }

}
