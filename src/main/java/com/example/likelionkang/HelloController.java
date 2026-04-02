package com.example.likelionkang;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + ResposeBody 컨트롤러
@RequestMapping("/api") // 엔드포인트가 어떻게 되냐
public class HelloController {

    @GetMapping("/hello") //HTTP Get 요청을 처리
    public String hello() {
        return "hello world";
    }
}
