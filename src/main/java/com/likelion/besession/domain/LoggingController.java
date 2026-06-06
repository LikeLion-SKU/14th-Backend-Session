package com.likelion.besession.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class LoggingController {
    @GetMapping("/logs")
    public String logTest() {

        log.trace("Trace - 가장 상세한 흐름 정보");
        log.debug("debug - 디버깅용 파라미터 값");
        log.info("info - 정상 처리 흐름");
        log.warn("warn - 경고 서비스 정상 동작");
        log.error("error - 오류 발생");
        return "로그 테스트 완료!";
    }
}
