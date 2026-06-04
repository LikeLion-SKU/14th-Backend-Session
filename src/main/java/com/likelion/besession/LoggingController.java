package com.likelion.besession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoggingController {

    @GetMapping("/logs")
    public String logTest(){
        log.trace("Trace - 가장 상세한 흐름 정보");
        log.debug("Debug - 디버깅용 파라미터 값");
        log.info("Info - 정상 처리 흐름");
        log.warn("Warn - 잘못된 입력, 주의 필요");
        log.error("Error - 오류 발생");

        return "로그 테스트 완료!";
    }


}
